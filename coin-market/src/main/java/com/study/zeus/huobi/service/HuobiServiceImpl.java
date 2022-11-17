package com.study.zeus.huobi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.zeus.common.Constant;
import com.study.zeus.core.AbstractWebsocketServer;
import com.study.zeus.entity.DetailDO;
import com.study.zeus.entity.KlineDO;
import com.study.zeus.entity.MarketDetail;
import com.study.zeus.job.PushJob;
import com.study.zeus.proto.Response;
import com.study.zeus.service.KlineService;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class HuobiServiceImpl implements HuobiService {

    private static final Logger LOG = LoggerFactory.getLogger(HuobiServiceImpl.class);

//    @Value("${hb.sub.symbol}")
    private String[] subSymbols;

    @Autowired
    private KlineService klineService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PushJob pushJob;

    @Override
    public void onReceive(String message) {
        LOG.info("msg: [{}]", message);
        if (message.contains("subbed")) {
            return;
        }

        JSONObject json = JSONObject.parseObject(message);
        //订阅通道
        String ch = json.getString("ch");
        if (ch.contains("detail") && ch.contains("trade")) {
            tradeHandle(json);
        } else if (ch.contains("kline")) {
            klineHandle(json);
        } else if (ch.contains("depth")) {
            depthHandle(json);
        } else if (ch.contains("detail") && !ch.contains("trade")) {
            detailHandle(json);
        }
    }

    @Override
    public List<String> channelCache() {
        return Arrays.asList(subSymbols);
    }

    /**
     * 24小时成交详情
     *
     * @param json
     */
    private void detailHandle(JSONObject json) {
        String ch = json.getString("ch");
        String[] str = ch.split("\\.");
        String pair = str[1];
        String symbol = pair.split("usdt")[0].toUpperCase() + "/USDT";
        String dd = stringRedisTemplate.opsForValue().get("coin:rate:usd_cny");
//        float rate = Float.valueOf(dd);
        DetailDO detailDO = JSON.parseObject(json.getString("tick"), DetailDO.class);

        float diff = detailDO.getClose() - detailDO.getOpen();
        float cny = detailDO.getClose() * 1;
        float percent = diff * 100 / detailDO.getOpen();
        detailDO.setDiff(diff);
        detailDO.setCny(cny);
        detailDO.setPercent(percent);
        detailDO.setTs(json.getLong("ts"));
        detailDO.setPair(pair);
        detailDO.setSymbol(symbol);
        AbstractWebsocketServer.senMessage(detailDO, ch, "detail", AbstractWebsocketServer.detailPool);
//        pushJob.addTrades(symbol,new MarketDetail(JSON.toJSONString(detailDO)));
        redisTemplate.opsForValue().set(String.format(Constant.TICKER_KEY, pair), JSON.toJSONString(detailDO));
    }


    /**
     * K线处理器
     *
     * @param json
     */
    private void klineHandle(JSONObject json) {
        String ch = json.getString("ch");
        String[] str = ch.split("\\.");
        String symbol = str[1];
        String kType = str[3];
        String pair = symbol.split("usdt")[0].toUpperCase() + "/USDT";
        KlineDO klineDO = JSON.parseObject(json.getString("tick"), KlineDO.class);
        klineDO.setSymbol(symbol);
        klineDO.setkType(kType);
        klineDO.setType("kline");
        klineDO.setPair(pair);
        klineDO.setkTime(Integer.valueOf(klineDO.getId()));
        klineDO.setId(symbol + klineDO.getId() + kType);
        AbstractWebsocketServer.senMessage(klineDO, ch, "kline", AbstractWebsocketServer.klinePool);
        //pushMessageToChannel(klineDO, ch, "kline", AbstractWebsocketServer.klinePool);
        klineService.updateKline(klineDO);

    }


    /**
     * 成交处理器
     *
     * @param json
     */
    private void tradeHandle(JSONObject json) {
        String ch = json.getString("ch");
        //TODO USDT 汇率暂时写死
        DetailDO detailDO = new DetailDO();
        //logger.info("trade detail: [{}]", json.toString());
    }


    /**
     * 深度处理
     *
     * @param json
     */
    private void depthHandle(JSONObject json) {
        String ch = json.getString("ch");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json.getString("tick"));
        AbstractWebsocketServer.senMessage(jsonObject, ch, "depth", AbstractWebsocketServer.depthPool);
        //pushMessageToChannel(jsonObject, ch, "depth", AbstractWebsocketServer.depthPool);
    }


    /**
     * 推送消息到客户端
     *
     * @param object
     * @param channel
     * @param event
     */
    private void pushMessageToChannel(Object object, String channel, String event, Map<String, NioSocketChannel> map) {
        if (null != map) {
            Iterator<Map.Entry<String, NioSocketChannel>> entryIterator = map.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, NioSocketChannel> entry = entryIterator.next();
                String clientId = entry.getKey();
                if (channel.contains(clientId)) {
                    NioSocketChannel value = entry.getValue();
                    value.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(Response.sucess(object, channel, event))));
                }
            }
        }
    }
}

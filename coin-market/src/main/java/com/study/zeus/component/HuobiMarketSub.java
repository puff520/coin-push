package com.study.zeus.component;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huobi.client.MarketClient;
import com.huobi.constant.HuobiOptions;
import com.huobi.service.huobi.HuobiMarketService;
import com.huobi.wss.handle.WssMarketHandle;
import com.study.zeus.entity.*;
import com.study.zeus.job.PushJob;
import com.study.zeus.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HuobiMarketSub {

    private ExecutorService executor = new ThreadPoolExecutor(30, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), new ThreadPoolExecutor.AbortPolicy());

    @Autowired
    private RedisTemplate redisTemplate;


    MarketClient marketClient = MarketClient.create(new HuobiOptions());

    @Autowired
    private PushJob pushJob;

    private String URL = "wss://api.huobi.pro/ws";//现货行情地址
    WssMarketHandle wssMarketDetailHandle = new WssMarketHandle(URL);
    WssMarketHandle wssMarketKlineHandle = new WssMarketHandle(URL);
    WssMarketHandle wssMarketDepthHandle = new WssMarketHandle(URL);

    WssMarketHandle wssMarketTradeHandle = new WssMarketHandle(URL);


    @PostConstruct
    private void subTopic() {
        executor.submit(new HandleDetailThread());
        executor.submit(new HandleKlineThread());
        executor.submit(new HandleDepthThread());
        executor.submit(new HandleTradeDetailThread());
    }

    public class HandleDetailThread implements Runnable {
        @Override
        public void run() {
            try {
                List<Currency> currencyList = getCacheList();
                List<String> channels = Lists.newArrayList();
                for (Currency currency : currencyList) {
                    channels.add("market." + currency.getName().toLowerCase() + "usdt.detail");
                }
                wssMarketDetailHandle.sub(channels, response -> {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    String ch = jsonObject.getString("ch");
                    int pri = ch.indexOf("market.") + 7;
                    int fix = ch.indexOf("usdt.detail");
                    String symbol = ch.substring(pri, fix).toUpperCase();

                    JSONObject tickObject = jsonObject.getJSONObject("tick");
                    BigDecimal amount = tickObject.getBigDecimal("amount");
                    Integer count = tickObject.getInteger("count");
                    BigDecimal open = tickObject.getBigDecimal("open");
                    BigDecimal close = tickObject.getBigDecimal("close");
                    BigDecimal low = tickObject.getBigDecimal("low");
                    BigDecimal high = tickObject.getBigDecimal("high");
                    BigDecimal vol = tickObject.getBigDecimal("vol");

                    DayMarket dayMarket = new DayMarket();
                    dayMarket.setCount(count);
                    dayMarket.setOpen(open);
                    dayMarket.setNow_price(close);
                    dayMarket.setChange(amount);
                    dayMarket.setHigh(high);
                    dayMarket.setVolume(vol);
                    dayMarket.setLow(low);
                    List<Currency> collect = currencyList.stream().filter(s -> s.getName().equals(symbol)).collect(Collectors.toList());
                    dayMarket.setCurrency_id(collect.get(0).getId());
                    MarketDetail.setDayMarket(dayMarket);
                    pushJob.pushDayMarket(dayMarket);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class HandleKlineThread implements Runnable {
        @Override
        public void run() {
            try {

                List<Currency> currencyList = getCacheList();
                List<String> channels = Lists.newArrayList();
                for (Currency currency : currencyList) {
                    channels.add("market." + currency.getName() + "usdt.kline.1min");
                    String topic = HuobiMarketService.WEBSOCKET_MARKET_DETAIL_TOPIC
                            .replace("$symbol", symbol);
                }
                wssMarketKlineHandle.sub(channels, response -> {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    String ch = jsonObject.getString("ch");
                    int pri = ch.indexOf("market.") + 7;
                    int fix = ch.indexOf("usdt.kline.");
                    String symbol = ch.substring(pri, fix).toUpperCase();

                    JSONObject tickObject = jsonObject.getJSONObject("tick");
                    Long time = jsonObject.getLong("ts");
                    BigDecimal amount = tickObject.getBigDecimal("amount");
                    Integer count = tickObject.getInteger("count");
                    BigDecimal open = tickObject.getBigDecimal("open");
                    BigDecimal close = tickObject.getBigDecimal("close");
                    BigDecimal low = tickObject.getBigDecimal("low");
                    BigDecimal high = tickObject.getBigDecimal("high");
                    BigDecimal vol = tickObject.getBigDecimal("vol");

                    Kline kline = new Kline();
                    kline.setCount(count);
                    kline.setOpen(open);
                    kline.setClose(close);
                    kline.setLow(low);
                    kline.setHigh(high);
                    kline.setVolume(vol);
                    kline.setPeriod("1min");
                    kline.setTime(time);
                    kline.setCurrency_name(symbol);
                    MarketDetail.setKline(kline);
                    pushJob.pushKline(kline);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class HandleDepthThread implements Runnable {
        @Override
        public void run() {
            try {
                List<Currency> currencyList = getCacheList();
                List<String> channels = Lists.newArrayList();
                for (Currency currency : currencyList) {
                    channels.add("market." + currency.getName() + "usdt.depth.step1");
                }
                wssMarketDepthHandle.sub(channels, response -> {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    String ch = jsonObject.getString("ch");
                    int pri = ch.indexOf("market.") + 7;
                    int fix = ch.indexOf("usdt.depth.");
                    String symbol = ch.substring(pri, fix).toUpperCase();
                    JSONObject tickObject = jsonObject.getJSONObject("tick");
                    Object bids = tickObject.get("bids");
                    Object asks = tickObject.get("asks");
                    MarketDepth marketDepth = new MarketDepth();
                    marketDepth.setAsks(asks);
                    marketDepth.setBids(bids);
                    List<Currency> collect = currencyList.stream().filter(s -> s.getName().equals(symbol)).collect(Collectors.toList());
                    marketDepth.setCurrency_id(collect.get(0).getId());
                    MarketDetail.setMarket_depth(marketDepth);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class HandleTradeDetailThread implements Runnable {
        @Override
        public void run() {
            try {
                List<Currency> currencyList = getCacheList();
                List<String> channels = Lists.newArrayList();
                for (Currency currency : currencyList) {
                    channels.add("market." + currency.getName() + "usdt.trade.detail");
                }
                wssMarketTradeHandle.sub(channels, response -> {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    String ch = jsonObject.getString("ch");
                    int pri = ch.indexOf("market.") + 7;
                    int fix = ch.indexOf("usdt.trade.");
                    String symbol = ch.substring(pri, fix).toUpperCase();
                    JSONObject tickObject = jsonObject.getJSONObject("tick");
                    TradeDetail tradeDetail = new TradeDetail();
                    JSONArray jsonArray = tickObject.getJSONArray("data");
                    for (Object data : jsonArray) {
                        JSONObject object = (JSONObject) data;
                        object.put("time", DateUtils.timeToString(new Date()));
                    }
                    tradeDetail.setData(jsonArray);
                    List<Currency> collect = currencyList.stream().filter(s -> s.getName().equals(symbol)).collect(Collectors.toList());
                    tradeDetail.setCurrency_id(collect.get(0).getId());
                    MarketDetail.setTradeDetail(tradeDetail);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Currency> getCacheList() {
        String listValue = (String) redisTemplate.opsForValue().get("currency");
        List<Currency> currencyList = JSONArray.parseArray(listValue, Currency.class);
        if (CollUtil.isNotEmpty(currencyList)) {
            return currencyList;
        }
        return new LinkedList<>();
    }


}

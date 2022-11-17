//package com.study.zeus.component;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.huobi.client.MarketClient;
//import com.huobi.client.req.market.SubCandlestickRequest;
//import com.huobi.client.req.market.SubMarketDepthRequest;
//import com.huobi.client.req.market.SubMarketDetailRequest;
//import com.huobi.constant.HuobiOptions;
//import com.huobi.constant.enums.CandlestickIntervalEnum;
//import com.huobi.constant.enums.DepthStepEnum;
//import com.huobi.wss.event.MarketTradeDetailSubResponse;
//import com.study.zeus.entity.DayMarket;
//import com.study.zeus.entity.Kline;
//import com.study.zeus.entity.MarketDepth;
//import com.study.zeus.entity.MarketDetail;
//import com.study.zeus.job.PushJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class HuobiMarketSub2 {
//
//    private ExecutorService executor = new ThreadPoolExecutor(30,
//            100,
//            0L,
//            TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<>(1024),
//            new ThreadPoolExecutor.AbortPolicy());
//    MarketClient marketClient = MarketClient.create(new HuobiOptions());
//    String symbol = "btcusdt";
//
//    @Autowired
//    private PushJob pushJob;
//
//
//    @PostConstruct
//    private void subTopic() {
//        executor.submit(new HandleDetailThread());
//        executor.submit(new HandleKlineThread());
//        executor.submit(new HandleDepthThread());
//    }
//
//    public class HandleDetailThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                marketClient.subMarketDetail(SubMarketDetailRequest
//                        .builder()
//                        .symbol(symbol)
//                        .build(), (marketDetailEvent) -> {
//                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(marketDetailEvent));
//                    JSONObject tickObject = jsonObject.getJSONObject("detail");
//                    BigDecimal amount = tickObject.getBigDecimal("amount");
//                    Integer count = tickObject.getInteger("count");
//                    BigDecimal open = tickObject.getBigDecimal("open");
//                    BigDecimal close = tickObject.getBigDecimal("close");
//                    BigDecimal low = tickObject.getBigDecimal("low");
//                    BigDecimal high = tickObject.getBigDecimal("high");
//                    BigDecimal vol = tickObject.getBigDecimal("vol");
//
//                    DayMarket dayMarket = new DayMarket();
//                    dayMarket.setCount(count);
//                    dayMarket.setOpen(open);
//                    dayMarket.setNow_price(close);
//                    dayMarket.setChange(amount);
//                    dayMarket.setHigh(high);
//                    dayMarket.setVolume(vol);
//                    dayMarket.setLow(low);
//                    MarketDetail.setDayMarket(dayMarket);
//
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    List<String> channels = Lists.newArrayList();
//        channels.add("market.BTC-USD.trade.detail");
//        wssMarketHandle.sub(channels, response -> {
//        logger.info("订阅TradeDetail数据用户收到的数据===============:{}", JSON.toJSON(response));
//        Long currentTimeMillis = System.currentTimeMillis();
//        MarketTradeDetailSubResponse event = JSON.parseObject(response, MarketTradeDetailSubResponse.class);
//        logger.info("tradeDetailEvent的ts为：{},当前的时间戳为：{},时间间隔为：{}毫秒", event.getTs(), currentTimeMillis, currentTimeMillis - event.getTs());
//    });
//
//    public class HandleKlineThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                marketClient.subCandlestick(SubCandlestickRequest.builder()
//                        .symbol(symbol)
//                        .interval(CandlestickIntervalEnum.MIN1)
//                        .build(), (candlestick) -> {
//                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(candlestick));
//                    JSONObject tickObject = jsonObject.getJSONObject("candlestick");
//                    Long time = jsonObject.getLong("ts");
//                    BigDecimal amount = tickObject.getBigDecimal("amount");
//                    Integer count = tickObject.getInteger("count");
//                    BigDecimal open = tickObject.getBigDecimal("open");
//                    BigDecimal close = tickObject.getBigDecimal("close");
//                    BigDecimal low = tickObject.getBigDecimal("low");
//                    BigDecimal high = tickObject.getBigDecimal("high");
//                    BigDecimal vol = tickObject.getBigDecimal("vol");
//
//                    Kline kline = new Kline();
//                    kline.setCount(count);
//                    kline.setOpen(open);
//                    kline.setClose(close);
//                    kline.setLow(low);
//                    kline.setHigh(high);
//                    kline.setVolume(vol);
//                    kline.setPeriod("1min");
//                    kline.setTime(time);
//                    MarketDetail.setKline(kline);
//
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public class HandleDepthThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                marketClient.subMarketDepth(SubMarketDepthRequest
//                        .builder()
//                        .symbol(symbol)
//                        .step(DepthStepEnum.STEP1)
//                        .build(), (marketDepthEvent) -> {
//                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(marketDepthEvent));
//                    JSONObject tickObject = jsonObject.getJSONObject("depth");
//                    Object bids = tickObject.get("bids");
//                    Object asks = tickObject.get("asks");
//                    MarketDepth marketDepth = new MarketDepth();
//                    marketDepth.setAsks(asks);
//                    marketDepth.setBids(bids);
//                    MarketDetail.setMarket_depth(marketDepth);
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

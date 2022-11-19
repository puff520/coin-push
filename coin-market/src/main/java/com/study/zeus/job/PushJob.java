package com.study.zeus.job;


import com.alibaba.fastjson.JSON;
import com.study.zeus.entity.DayMarket;
import com.study.zeus.entity.Kline;
import com.study.zeus.entity.MarketDetail;
import com.study.zeus.utils.SocketUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PushJob {


    public void pushDayMarket(DayMarket dayMarket) {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("daymarket", JSON.toJSONString(dayMarket));
            }
        });
    }

    public void pushKline(Kline kline) {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("kline", JSON.toJSONString(kline));
            }
        });
    }


    @Scheduled(cron = "0/1 * * * * ?")
    public void pushDayDepth() {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("market_depth", JSON.toJSONString(MarketDetail.getMarket_depth()));
            }
        });
    }


    @Scheduled(cron = "0/1 * * * * ?")
    public void pushTradeDetail() {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("market_detail", JSON.toJSONString(MarketDetail.getTradeDetail()));
            }
        });
    }


    @Scheduled(cron = "0/20 * * * * ?")
    public void test4() {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("lever_trade", JSON.toJSONString(MarketDetail.getLever_trade()));
            }
        });
    }


    @Scheduled(cron = "0/20 * * * * ?")
    public void test5() {
        SocketUtil.connectMap.forEach((key, client) -> {
            if (client != null) {
                client.sendEvent("closed_microorder", JSON.toJSONString(MarketDetail.getClosed_microorder()));
            }
        });
    }


}

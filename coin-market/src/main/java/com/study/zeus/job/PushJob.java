package com.study.zeus.job;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.zeus.entity.DayMarket;
import com.study.zeus.entity.Kline;
import com.study.zeus.entity.MarketDepth;
import com.study.zeus.entity.MarketDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PushJob {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    /**
     * 收到变更记录 推送行情到客户端
     *
     * @param dayMarket
     */
    public void pushDayMarket(DayMarket dayMarket) {
        simpMessagingTemplate.convertAndSend("/topic/dayMarket", JSON.toJSONString(dayMarket));
    }



    /**
     * 收到变更记录 推送行情到客户端
     *
     * @param kline
     */
    public void pushKline(Kline kline) {
        simpMessagingTemplate.convertAndSend("/topic/kline", JSON.toJSONString(kline));
    }



    /**
     * 收到变更记录 推送行情到客户端
     *
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void pushDayDepth() {
        simpMessagingTemplate.convertAndSend("/topic/marketDepth", JSON.toJSONString(MarketDetail.getDayMarket()));
    }




    @Scheduled(cron = "0/1 * * * * ?")
    public void test4() {
        simpMessagingTemplate.convertAndSend("/topic/leverTrade", JSON.toJSONString(MarketDetail.getLever_trade()));
    }


    @Scheduled(cron = "0/1 * * * * ?")
    public void test5() {
        simpMessagingTemplate.convertAndSend("/topic/closedMicroOrder", JSON.toJSONString(MarketDetail.getClosed_microorder()));
    }


//    @Scheduled(cron = "0/5 * * * * ?")
//    public void test2() {
//        simpMessagingTemplate.convertAndSendToUser("10086", "/message", "点对点消息");
//    }
}

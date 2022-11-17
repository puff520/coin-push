package com.study.zeus.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("ws推送数据详情")
@Data
@AllArgsConstructor
public class MarketDetail {

    @ApiModelProperty("最近24小时交易详情")
    private static DayMarket dayMarket;
    @ApiModelProperty("K线数据")
    private static Kline kline;
    @ApiModelProperty("行情深度")
    private static MarketDepth market_depth;

    @ApiModelProperty("交易详情")
    private static TradeDetail tradeDetail;
    @ApiModelProperty("杠杆交易")
    private static LeverTrade lever_trade;
    @ApiModelProperty("关闭数据")
    private static ClosedMicroorder closed_microorder;


    public static void setDayMarket(DayMarket dayMarket) {
        MarketDetail.dayMarket = dayMarket;
    }

    public static void setKline(Kline kline) {
        MarketDetail.kline = kline;
    }

    public static void setMarket_depth(MarketDepth market_depth) {
        MarketDetail.market_depth = market_depth;
    }

    public static void setTradeDetail(TradeDetail tradeDetail) {
        MarketDetail.tradeDetail = tradeDetail;
    }

    public static void setLever_trade(LeverTrade lever_trade) {
        MarketDetail.lever_trade = lever_trade;
    }

    public static void setClosed_microorder(ClosedMicroorder closed_microorder) {
        MarketDetail.closed_microorder = closed_microorder;
    }


    public static DayMarket getDayMarket() {
        return dayMarket;
    }

    public static Kline getKline() {
        return kline;
    }

    public static MarketDepth getMarket_depth() {
        return market_depth;
    }

    public static LeverTrade getLever_trade() {
        return lever_trade;
    }

    public static ClosedMicroorder getClosed_microorder() {
        return closed_microorder;
    }

    public static TradeDetail getTradeDetail() {
        return tradeDetail;
    }
}

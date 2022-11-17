package com.huobi.wss.constants;

public class HuobiSwapsWSSConstants {

    //市场行情接口 订阅
    public static final String MARKET_KLINE_PERIOD_SUB = "market.$contract_code.kline.$period"; // 1.订阅 KLine 数据
    public static final String MARKET_DEPTH_SUB = "market.$contract_code.depth.$type"; // 2.订阅 Market Depth 数据
    public static final String MARKET_DETAIL_SUB = "market.$contract_code.detail";// 3.订阅 Market detail 数据


    //市场行情接口 请求
    public static final String MARKET_KLINE_PERIOD_REQ = "market.$contract_code.kline.$period"; // 1.请求 KLine 数据
    public static final String MARKET_DETAIL_REQ = "market.$contract_code.trade.detail";// 2.请求 Market detail 数据


    //交易接口
    public static final String ORDERS_SYMBOL = "orders.$symbol-$partition"; // 1.订阅订单成交数据


    //资产接口
    public static final String ACCOUNTS_SYMBOL = "accounts.$symbol-$partition"; // 1.订阅某个品种下的资产变动信息
    public static final String POSITIONS_SYMBOL = "positions.$symbol-$partition"; // 2.订阅某个品种下的持仓变动信息


}

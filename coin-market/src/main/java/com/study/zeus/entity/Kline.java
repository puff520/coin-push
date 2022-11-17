package com.study.zeus.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("K线数据")
@Data
public class Kline {

    @ApiModelProperty("开盘价")
    private  String symbol;
    @ApiModelProperty("开盘价")
    private BigDecimal open;
    @ApiModelProperty("最低价")
    private BigDecimal low;
    @ApiModelProperty("最高价")
    private BigDecimal high;
    @ApiModelProperty("收盘价")
    private BigDecimal close;
    @ApiModelProperty("交易次数")
    private Integer count ;
    @ApiModelProperty("成交额, 即 sum(每一笔成交价 * 该笔的成交量)")
    private BigDecimal volume;
    @ApiModelProperty("系统响应时间")
    private Long time;
    @ApiModelProperty("涨幅")
    private BigDecimal change;
    @ApiModelProperty("K线周期")
    private String period;
    @ApiModelProperty("币种名称")
    private String currency_name ;


    public BigDecimal getChange() {
        Double increase = close.subtract(open).doubleValue();
        Double perRate = increase/(open.doubleValue());
        BigDecimal change = new BigDecimal(perRate*100).setScale(3,BigDecimal.ROUND_HALF_UP);
        return change;
    }

}

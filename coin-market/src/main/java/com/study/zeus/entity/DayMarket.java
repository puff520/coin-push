package com.study.zeus.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel("最近24小时交易详情")
@Data
public class DayMarket {

    @ApiModelProperty("id")
    private Long legal_id = 3L;

    @ApiModelProperty("币种id")
    private Long currency_id;
    
    @ApiModelProperty("交易次数")
    private Integer count ;
    @ApiModelProperty("开盘价")
    private BigDecimal open;
    @ApiModelProperty("当前价（close）")
    private BigDecimal now_price;

    @ApiModelProperty("涨幅")
    private BigDecimal change;

    @ApiModelProperty("以报价币种计量的交易量")
    private BigDecimal volume;

    @ApiModelProperty("本阶段最高价")
    private BigDecimal high;

    @ApiModelProperty("本阶段最低价")
    private BigDecimal low;


    public BigDecimal getChange() {
        BigDecimal addNum = now_price.subtract(open);
        BigDecimal change = addNum.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN);
        return change;
    }
}

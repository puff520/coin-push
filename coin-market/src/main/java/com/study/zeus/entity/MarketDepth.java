package com.study.zeus.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("行情深度")
@Data
public class MarketDepth {

    @ApiModelProperty("当前的所有买单 [price, size]")
    private Object bids;
    @ApiModelProperty("当前的所有卖单 [price, size]")
    private Object asks;
    @ApiModelProperty("币种id")
    private Long currency_id ;
    @ApiModelProperty("交易id")
    private Integer legal_id = 3;


}

package com.study.zeus.entity;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel("交易明细")
@Data
public class TradeDetail {

    @ApiModelProperty("id")
    private Long legal_id = 3L;

    @ApiModelProperty("币种id")
    private Long currency_id;

    @ApiModelProperty("币种id")
    private JSONArray data;
}

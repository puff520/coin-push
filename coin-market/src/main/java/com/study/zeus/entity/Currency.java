package com.study.zeus.entity;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@ApiModel("币种表")
public class Currency extends BaseEntity {


    private String name;
    private String getAddress;
    private Long sort;
    private String logo;
    private Long isDisplay;
    private Double minNumber;
    private Double maxNumber;
    private Double rate;
    private Long isLever;
    private Long isLegal;
    private Long isMatch;
    private Long isMicro;
    private Long insurancable;
    private Long showLegal;
    private String type;
    private Long blackLimt;
    private String key;
    private String contractAddress;
    private String totalAccount;
    private String collectAccount;
    private Long currencyDecimals;
    private Double rmbRelation;
    private Long decimalScale;
    private Double chainFee;
    private Double price;
    private Double microTradeFee;
    private Double microMin;
    private Double microMax;
    private Long microHoldtradeMax;
    private Integer createTime;


}

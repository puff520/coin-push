package com.study.zeus.entity;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("杠杆")
@Data
public class LeverTrade {

    private String trades_all = "";
    private String hazard_rate = "";
    private String profits_all = "";
    private String profits = "";
    private String trades_entrust = "";


}

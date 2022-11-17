package com.study.zeus.entity;

import lombok.Data;

@Data
public class Message {

    /**
     * 消息编码
     */
    private String code;

    /**
     * 来自（保证唯一）
     */
    private String form;

    /**
     * 去自（保证唯一）
     */
    private String to;

    /**
     * 内容
     */
    private String content = "111111";


}
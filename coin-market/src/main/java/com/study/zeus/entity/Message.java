package com.study.zeus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -5345570479231988220L;
    /**
     * 发送人
     */
    private String senderId;
    /**
     * 接收人
     */
    private String receiverId;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息体
     */
    private Object content;

    /**
     * 订阅的事件名称
     */
    private String event;

    /**
     * 创建的时间
     */
    private String time;


}


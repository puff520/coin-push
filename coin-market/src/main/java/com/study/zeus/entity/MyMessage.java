package com.study.zeus.entity;

/**
 * @Author: JCccc
 * @Date: 2022-07-23 9:05
 * @Description:
 */
public class MyMessage {

    private String type;

    private String content;

    private String from;

    private String to;

    private String channel;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
package com.study.zeus.controller;

import com.study.zeus.entity.MyMessage;
import com.study.zeus.utils.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class WebSocketController {
    public final static String SEND_TYPE_ALL = "ALL";
    public final static String SEND_TYPE_ALONE = "ALONE";
    @Autowired
    SocketUtil socketUtil;

    @PostMapping("/testSendMsg")
    public String testSendMsg(@RequestBody MyMessage myMessage) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", myMessage.getContent());

        //群发
        if (SEND_TYPE_ALL.equals(myMessage.getType())) {
            socketUtil.sendToAll(map, myMessage.getChannel());
            return "success";
        }
        //指定单人
        if (SEND_TYPE_ALONE.equals(myMessage.getType())) {
            socketUtil.sendToOne(myMessage.getTo(), map, myMessage.getChannel());
            return "success";
        }

        return "fail";
    }
}
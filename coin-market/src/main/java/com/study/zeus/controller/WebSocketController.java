package com.study.zeus.controller;

import com.study.zeus.entity.Message;
import com.study.zeus.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping({"/topic/hello"})
    public Result subscribeTime() {
        return Result.success("hello!");
    }

    @SubscribeMapping({"/topic/info/{classId}"})
    public Result subscribeState(@DestinationVariable String classId) {
        return Result.success("班级消息推送订阅成功!" + classId);
    }

    @SubscribeMapping({"/user/{name}/hello"})
    public Result subscribeParam(@DestinationVariable String name) {
        return Result.success("你好!" + name);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public Result hello(String requestMessage) {
        System.out.println("接收消息：" + requestMessage);
        return Result.success("服务端接收到你发的：" + requestMessage);
    }

    @GetMapping("/sendMsgToUser")
    public String sendMsgByUser(String name, String msg) {
        // /user/{name}/hello
        simpMessagingTemplate.convertAndSendToUser(name, "/hello", msg);
        return "success";
    }

    @GetMapping("/sendMsgToAll")
    public String sendMsgByAll(int classId, String msg) {
        // /topic/info/{classId}
        simpMessagingTemplate.convertAndSend("/topic/info/" + classId, msg);
        return "success";
    }

    /**
     * 广播
     *
     * @param msg
     */
    @ResponseBody
    @RequestMapping("/pushToAll")
    public void subscribe(@RequestBody Message msg) {
        simpMessagingTemplate.convertAndSend("/topic/all", msg.getContent());
    }
}
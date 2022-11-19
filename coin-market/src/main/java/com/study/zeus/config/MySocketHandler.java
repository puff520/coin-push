package com.study.zeus.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.study.zeus.entity.Message;
import com.study.zeus.utils.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;


/**
 * @Author: JCccc
 * @Description:
 * @Date: 2022/6/23 21:21
 */
@Component
public class MySocketHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SocketIOServer socketIoServer;

    @Autowired
    SocketUtil socketUtil;


    @PostConstruct
    private void start() {
        try {
//            socketIoServer.addEventListener("chatEvent",
//                    Message.class, (client, data, ackRequest) ->
//                            socketIoServer.getBroadcastOperations().sendEvent("chatEvent", data));
            socketIoServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        try {
            socketIoServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnEvent("login")
    public void loginEvent(Message data) {
        log.info("接收到前端发来的数据:{}", data);
        if (data != null) {
            // 全部发送
            SocketUtil.connectMap.forEach((key, client) -> {
                if (client != null) {
                    client.sendEvent("login", "ok");
                }
            });
        }
    }


    @OnConnect
    public void connect(SocketIOClient client) {
        String uid = client.getSessionId().toString();
        SocketUtil.connectMap.remove(uid);
        SocketUtil.connectMap.put(uid, client);
        log.info("客户端uid: " + uid + "已连接");
    }


    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String uid = client.getSessionId().toString();
        log.info("客户端uid:" + uid + "断开连接");
        if (uid != null) {
            Set<String> keySet = SocketUtil.connectMap.keySet();
            for (String key : keySet) {
                if (uid.equals(key)) {
                    SocketIOClient socketIOClient = SocketUtil.connectMap.get(key);
                    if (StrUtil.isBlankIfStr(socketIOClient)) {
                        socketIOClient.disconnect();
                        log.info("用户 {} 端断开链接", uid);
                    }

                }
            }
        }
        SocketUtil.connectMap.remove(uid, client);
    }

}
package com.study.zeus.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: JCccc
 * @Description:
 * @Date: 2022/6/23 21:28
 */
@Component
public class SocketUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //暂且把用户&客户端信息存在缓存
    public static ConcurrentMap<String, SocketIOClient> connectMap = new ConcurrentHashMap<>();


    public void sendToAll(Map<String, Object> msg, String sendChannel) {
        if (connectMap.isEmpty()) {
            return;
        }
        //给在这个频道的每个客户端发消息
        for (Map.Entry<String, SocketIOClient> entry : connectMap.entrySet()) {
            entry.getValue().sendEvent(sendChannel, msg);
        }
    }

    public void sendToOne(String userFlag, Map<String, Object> msg, String sendChannel) {
        //拿出某个客户端信息
        SocketIOClient socketClient = getSocketClient(userFlag);
        if (Objects.nonNull(socketClient)) {
            //单独给他发消息
            socketClient.sendEvent(sendChannel, msg);
        }
    }


    /**
     * 识别出客户端
     *
     * @param userFlag
     * @return
     */
    public SocketIOClient getSocketClient(String userFlag) {
        SocketIOClient client = null;
        if (StringUtils.hasLength(userFlag) && !connectMap.isEmpty()) {
            for (String key : connectMap.keySet()) {
                if (userFlag.equals(key)) {
                    client = connectMap.get(key);
                }
            }
        }
        return client;
    }


}
package com.study.zeus.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.ResultSetWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lanni
 * @date 2020/9/10 17:15
 * WebSocket拦截器
 **/
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("---------握手开始---------");
        log.info("访问路径:"+serverHttpRequest.getURI());
        boolean flag = false;
        // 获得请求参数
        Map<String, String> paramMap = new HashMap<>();
        HttpUtil.decodeParamMap(serverHttpRequest.getURI().getQuery(), CharsetUtil.CHARSET_UTF_8);
        String token = paramMap.get("token");
        if(token != null && !"".equals(token)) {
            //验证token
            String adminId = stringRedisTemplate.opsForValue().get(token);
            if (StrUtil.isNotBlank(adminId)) {
                String realToken = stringRedisTemplate.opsForValue().get(adminId);
                flag =  token.equals(realToken);
            }
        }
        serverHttpResponse.getHeaders().setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        if (!flag) {
            log.info("用户未登录");
            serverHttpResponse.getBody().write(JSONObject.toJSONBytes("用户未登录"));
        }
        return flag;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("握手成功...");
        try {
            serverHttpResponse.getBody().write(JSONObject.toJSONBytes("WebSocket连接成功"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
package com.study.zeus.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.io.IOException;

@EnableScheduling
@Configuration
public class CurrencyRateTask {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateTask.class);
    private final String RATE = "coin:rate";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Scheduled(cron = "0 0/30 * * * ?")
    private void task() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request
                .Builder()
                .url("https://www.okx.com/api/v5/market/exchange-rate")
                .get()
                .build();

        final Call call = client.newCall(request);
        new Thread(() -> {
            try {
                Response response = call.execute();
                String result = response.body().string();
                JSONObject json = JSONObject.parseObject(result);
                if (!StringUtils.isEmpty(json)) {
                    String code = json.getString("code");
                    if(code.equals("0")){
                        String dataStr = json.getString("data");
                        if (!StringUtils.isEmpty(dataStr)) {
                            JSONArray array = json.getJSONArray("data");
                            JSONObject usdObject = (JSONObject) array.get(0);
                            String usdCnyRate = usdObject.getString("usdCny");
                            redisTemplate.opsForValue().set(RATE + "usdCny", usdCnyRate);
                            logger.info("写入汇率: [{}] ---- [{}]","usdCny",usdCnyRate);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

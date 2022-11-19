package com.study.zeus.job;


import com.alibaba.fastjson.JSON;
import com.study.zeus.dao.CurrencyRepository;
import com.study.zeus.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheCurrencyJob {


    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private RedisTemplate redisTemplate;


    @Scheduled(fixedRate = 50000)
    public void test() {
        List<Currency> currencyList = currencyRepository.findByType("usdt");
        redisTemplate.opsForValue().set("currency", JSON.toJSONString(currencyList));
    }

}

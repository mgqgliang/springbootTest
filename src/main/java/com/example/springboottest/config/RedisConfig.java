package com.example.springboottest.config;

import com.example.springboottest.common.CommonRedisBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    /**
     * 将redis加入到Spring的Bean中
     * @param redisTemplate 消息
     * @return
     */
    @Bean
    public CommonRedisBean redisBean(StringRedisTemplate redisTemplate){
        CommonRedisBean bean = new CommonRedisBean(redisTemplate);
        return bean;
    }
}

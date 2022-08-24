package com.example.springboottest.common;

import com.example.springboottest.util.EntityUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis配置
 */
public class CommonRedisBean {
    @Nullable
    private StringRedisTemplate redisTemplate;

    public CommonRedisBean(StringRedisTemplate redisTemplate) {
        this.redisTemplate  = redisTemplate;
    }

    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, List<?> values) {
        redisTemplate.delete(key);
        for (Object o : values) {
            redisTemplate.opsForList().rightPush(key, EntityUtils.getMessageFromVo(o));
        }

    }

    public List<String> getList(String key) {
        Long l = redisTemplate.opsForList().size(key);
        return redisTemplate.opsForList().range(key, 0, l);
    }

    public void setMap(String key, Object values) {
        redisTemplate.delete(key);
        redisTemplate.opsForHash().putAll(key, EntityUtils.getVoToMap(values));

    }

    public Map<String, Object> getMap(String key) {
        Map<Object, Object> m = redisTemplate.opsForHash().entries(key);
        Map<String,Object> r = new HashMap<>(16);
        for (Object objKey : m.keySet()) {
            r.put(objKey.toString(), m.get(objKey));
        }
        return r;
    }
    public List<String> getMap(String key,Class<?> clazz) {
        return null;
    }
}

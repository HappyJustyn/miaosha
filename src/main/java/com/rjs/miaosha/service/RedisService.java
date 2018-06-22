package com.rjs.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.rjs.miaosha.redis.prefix.KeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/9 22:29
 */
@Slf4j
@Service
public class RedisService {

    @Resource
    private JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    // 获取
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            String str = jedis.get(prefix.getPrefix() + key);
            return JSON.parseObject(str, clazz);
        } finally {
            returnResource(jedis);
        }
    }

    // 存储
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        if (value == null || key == null) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (prefix.expireSeconds() <= 0) {
                jedis.set(prefix.getPrefix() + key, JSON.toJSONString(value));
            } else {
                // 设置有效期
                jedis.setex(prefix.getPrefix() + key, prefix.expireSeconds(), JSON.toJSONString(value));
            }
            log.info("存入redis,key={}", (prefix.getPrefix() + key));
            return true;
        } finally {
            returnResource(jedis);
        }
    }

    // 判断key是否存在
    public boolean exists(KeyPrefix prefix, String key) {
        if (key == null) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.exists(prefix.getPrefix() + key);
        } finally {
            returnResource(jedis);
        }
    }

    // 数字值增加一
    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.incr(prefix.getPrefix() + key);
        } finally {
            returnResource(jedis);
        }
    }

    // 数字值减少一
    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.decr(prefix.getPrefix() + key);
        } finally {
            returnResource(jedis);
        }
    }

    // 删除
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.del(prefix.getPrefix() + key) > 0;
        } finally {
            returnResource(jedis);
        }
    }

}

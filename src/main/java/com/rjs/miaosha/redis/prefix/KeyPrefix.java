package com.rjs.miaosha.redis.prefix;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/10 12:36
 */
public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}

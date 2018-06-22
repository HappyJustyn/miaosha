package com.rjs.miaosha.redis.prefix;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/10 12:38
 */
@AllArgsConstructor
@Data
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        this.expireSeconds = 0; // 0-为0程序不设置有效期，即永不过期
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}

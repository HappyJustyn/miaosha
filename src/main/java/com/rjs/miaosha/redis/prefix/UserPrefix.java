package com.rjs.miaosha.redis.prefix;

import com.rjs.miaosha.config.CookieProperties;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/10 12:56
 */
public class UserPrefix extends BasePrefix {

    private int expireSeconds;

    public UserPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserPrefix getByName = new UserPrefix(CookieProperties.MAX_AGE, "name");
    public static UserPrefix getByAge = new UserPrefix(CookieProperties.MAX_AGE, "age");
    public static UserPrefix getByCookie = new UserPrefix(CookieProperties.MAX_AGE, "cookie");
    public static UserPrefix getById = new UserPrefix(CookieProperties.FOREVER_AGE, "id");

}

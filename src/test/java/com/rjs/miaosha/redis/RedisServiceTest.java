package com.rjs.miaosha.redis;

import com.rjs.miaosha.redis.prefix.UserPrefix;
import com.rjs.miaosha.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/10 11:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Resource
    private RedisService redisService;

    @Test
    public void get() {
        String str = redisService.get(UserPrefix.getByName,"1",String.class);
        System.out.println(str);
    }

    @Test
    public void set() {
        Assert.isTrue(true == redisService.set(UserPrefix.getByAge,"age","12"),"error");
    }
}
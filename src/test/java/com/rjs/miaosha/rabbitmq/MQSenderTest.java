package com.rjs.miaosha.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rjs.miaosha.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 21:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQSenderTest {

    @Resource
    private MQSender mqSender;

    @Test
    public void sendMessage() {
    }

    /*
    @Test
    public void send() {
        mqSender.send("hello world");
    }

    @Test
    public void sendTopic1() {
        mqSender.sendTopic1("hello world");
    }

    @Test
    public void sendTopic2() {
        mqSender.sendTopic2("hello world");
    }

    @Test
    public void sendFanout() {
        mqSender.sendFanout("hello world");
    }

    @Test
    public void sendHeaders() {
        mqSender.sendHeaders("hello world");
    }
    */
}
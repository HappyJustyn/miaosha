package com.rjs.miaosha.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rjs.miaosha.dto.MiaoshaMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 20:40
 */
@Slf4j
@Component
public class MQSender {

    @Resource
    private AmqpTemplate amqpTemplate;

    /*
    public void send(Object message) {
        amqpTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE_NAME, "queue", JSON.toJSONString(message));
    }

    public void sendTopic1(Object message) {
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, "topic.key1", JSON.toJSONString(message));
    }

    public void sendTopic2(Object message) {
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, "topic.key2", JSON.toJSONString(message));
    }

    public void sendFanout(Object message) {
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE_NAME, "", JSON.toJSONString(message));
    }

    public void sendHeaders(Object message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key1", "value1");
        messageProperties.setHeader("key2", "value2");
        Message msg = new Message(JSON.toJSONString(message).getBytes(), messageProperties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE_NAME, "", msg);
    }
    */

    public void sendMessage(MiaoshaMessageDTO miaoshaMessageDTO) {
        log.info("【请求入队】,message={}", miaoshaMessageDTO.toString());
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, JSON.toJSONString(miaoshaMessageDTO));
    }
}

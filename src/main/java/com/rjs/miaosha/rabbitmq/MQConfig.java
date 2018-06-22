package com.rjs.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 消息队列配置类
 * @Author: Justyn
 * @Date: 2018/6/20 20:41
 */
@Configuration
public class MQConfig {

    /*
    public static final String QUEUE_NAME = "queue";
    public static final String HEADERS_QUEUE_NAME = "headersQueue";
    public static final String TOPIC_QUEUE_NAME1 = "top.queue1";
    public static final String TOPIC_QUEUE_NAME2 = "top.queue2";
    public static final String DIRECT_EXCHANGE_NAME = "directExchange";
    public static final String TOPIC_EXCHANGE_NAME = "exchange";
    public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";
    public static final String HEADERS_EXCHANGE_NAME = "headersExchange";
    */
    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    @Bean
    public Queue queue() {
        return new Queue(MQConfig.MIAOSHA_QUEUE, true);
    }

    /*
    @Bean
    public Queue queue() {
        return new Queue(MQConfig.QUEUE_NAME, true);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(MQConfig.TOPIC_QUEUE_NAME1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(MQConfig.TOPIC_QUEUE_NAME2, true);
    }

    @Bean
    public Queue headersQueue() {
        return new Queue(MQConfig.HEADERS_QUEUE_NAME, true);
    }

    // topic交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MQConfig.TOPIC_EXCHANGE_NAME);
    }

    // fanout交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(MQConfig.FANOUT_EXCHANGE_NAME);
    }

    // headers交换机
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(MQConfig.HEADERS_EXCHANGE_NAME);
    }

    // direct交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MQConfig.DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Binding directBinding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("queue");
    }

    @Bean
    Binding toppicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    Binding toppicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    @Bean
    Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    @Bean
    Binding headersBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
    */


}

package com.rjs.miaosha.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rjs.miaosha.dto.MiaoshaMessageDTO;
import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.exception.CustomException;
import com.rjs.miaosha.model.MiaoshaOrder;
import com.rjs.miaosha.model.OrderInfo;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.service.GoodsService;
import com.rjs.miaosha.service.MiaoshaOrderService;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 20:41
 */
@Slf4j
@Component
public class MQReceiver {

    @Resource
    private GoodsService goodsService;
    @Resource
    private MiaoshaOrderService miaoshaOrderService;


    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message) {
        log.info("【接收消息】,message={}", message);
        MiaoshaMessageDTO miaoshaMessageDTO = JSON.parseObject(message, new TypeReference<MiaoshaMessageDTO>(){});
        Long goodsId = miaoshaMessageDTO.getGoodsId();
        User user = miaoshaMessageDTO.getUser();

        // 判断库存，下单
        //判断库存
        GoodsVO goods = goodsService.getByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaOrderService.miaosha(user, goods);
    }

    /*
    @RabbitListener(queues = MQConfig.QUEUE_NAME)
    public void receive(String message) {
        System.out.println(message + "---------------------");
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME1)
    public void receiveTopic1(String message) {
        System.out.println(message + "-----------TOPIC_QUEUE_NAME1----------");
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME2)
    public void receiveTopic2(String message) {
        System.out.println(message + "-----------TOPIC_QUEUE_NAME2----------");
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE_NAME)
    public void receiveHeaders(byte[] message) {
        System.out.println(new String(message) + "-----------HEADERS_QUEUE_NAME----------");
    }
    */
}

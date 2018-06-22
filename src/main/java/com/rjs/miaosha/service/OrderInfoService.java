package com.rjs.miaosha.service;

import com.rjs.miaosha.enums.PayStatusEnum;
import com.rjs.miaosha.mapper.OrderInfoMapper;
import com.rjs.miaosha.model.MiaoshaOrder;
import com.rjs.miaosha.model.OrderInfo;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 13:15
 */
@Service
public class OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private MiaoshaOrderService miaoshaOrderService;

    @Transactional
    public OrderInfo createOrder(User user, GoodsVO goodsVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getMiaoshaPrice());
        orderInfo.setUserId(user.getId());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(PayStatusEnum.CREATE_NOT_PAY.getCode());
//        Long orderId = orderInfoMapper.insertOrderInfo(orderInfo);
        orderInfoMapper.insertOrderInfo(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVO.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderInfo.getId() );
        miaoshaOrderService.createOrder(miaoshaOrder);

        return orderInfo;
    }

    public OrderInfo getOrderById(Long id) {
        return orderInfoMapper.getOrderById(id);
    }

}

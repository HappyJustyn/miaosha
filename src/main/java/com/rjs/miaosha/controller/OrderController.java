package com.rjs.miaosha.controller;

import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.model.OrderInfo;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.service.GoodsService;
import com.rjs.miaosha.service.OrderInfoService;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.GoodsVO;
import com.rjs.miaosha.vo.OrderDetailVO;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/19 23:26
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private GoodsService goodsService;

    @RequestMapping("/detail")
    public ResultVO info(User user, @RequestParam("orderId") long orderId) {
        if(user == null) {
            return ResultUtil.error(ResultEnum.SESSION_OVERDUE);
        }
        OrderInfo order = orderInfoService.getOrderById(orderId);
        if(order == null) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVO goods = goodsService.getByGoodsId(goodsId);
        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrderInfo(order);
        vo.setGoods(goods);
        return ResultUtil.success(vo);
    }
}

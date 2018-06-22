package com.rjs.miaosha.vo;

import com.rjs.miaosha.model.OrderInfo;
import lombok.Data;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 7:44
 */
@Data
public class OrderDetailVO {
    private GoodsVO goods;
    private OrderInfo orderInfo;
}

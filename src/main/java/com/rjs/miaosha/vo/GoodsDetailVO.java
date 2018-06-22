package com.rjs.miaosha.vo;

import com.rjs.miaosha.model.User;
import lombok.Data;

/**
 * @Description: 商品详情传输对象
 * @Author: Justyn
 * @Date: 2018/6/19 22:07
 */
@Data
public class GoodsDetailVO {
    private int remainSeconds;
    private int miaoshaStatus;
    private GoodsVO goodsVO;
    private User user;
}

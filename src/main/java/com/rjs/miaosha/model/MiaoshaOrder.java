package com.rjs.miaosha.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "miaosha_order")
public class MiaoshaOrder {
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

}
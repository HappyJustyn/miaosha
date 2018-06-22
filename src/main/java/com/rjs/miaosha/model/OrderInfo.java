package com.rjs.miaosha.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "order_info")
public class OrderInfo {
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 收货地址id
     */
    @Column(name = "delivery_addr_id")
    private Long deliveryAddrId;

    /**
     * 冗余过来的商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 商品数量
     */
    @Column(name = "goods_count")
    private Integer goodsCount;

    /**
     * 商品单价
     */
    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    /**
     * 1-pc 2-android 3-ios
     */
    @Column(name = "order_channel")
    private Integer orderChannel;

    /**
     * 订单状态 0-新建未支付 1-已支付 2-已发货 3-已收货 4-已退款 5-已完成
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 支付时间
     */
    @Column(name = "pay_date")
    private Date payDate;

}
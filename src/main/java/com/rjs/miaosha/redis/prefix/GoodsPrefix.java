package com.rjs.miaosha.redis.prefix;

import com.rjs.miaosha.model.Goods;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 23:13
 */
public class GoodsPrefix extends  BasePrefix {

    public static GoodsPrefix goodsList = new GoodsPrefix(60, "goodsList");
    public static GoodsPrefix goodsDetail = new GoodsPrefix(60, "goodsDetail");
    public static GoodsPrefix goodsMiaoshaStock = new GoodsPrefix(0, "goodsMiaoshaStock");

    public GoodsPrefix(String prefix) {
        super(prefix);
    }

    public GoodsPrefix(int expireSeconds, String prefix ) {
        super(expireSeconds, prefix);
    }

}

package com.rjs.miaosha.mapper;

import com.rjs.miaosha.model.MiaoshaOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface MiaoshaOrderMapper extends Mapper<MiaoshaOrder> {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getOrderByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
}
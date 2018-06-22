package com.rjs.miaosha.mapper;

import com.rjs.miaosha.model.MiaoshaGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface MiaoshaGoodsMapper extends Mapper<MiaoshaGoods> {
    @Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId} and stock_count>0")
    public int reduceStockById(@Param("goodsId") Long goodsId);
}
package com.rjs.miaosha.mapper;

import com.rjs.miaosha.model.Goods;
import com.rjs.miaosha.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsMapper extends Mapper<Goods> {

    @Select("select g.*,m.miaosha_price,m.stock_count,m.start_date,m.end_date from goods g left join miaosha_goods m on g.id = m.goods_id")
    public List<GoodsVO> getGoodsVO();

    public GoodsVO getByGoodsId(@Param("goodsId") Long goodsId);

    @Update("update goods set goods_stock=goods_stock-1 where id=#{goodsId} and goods_stock>0")
    public int reduceStockById(@Param("goodsId") Long goodsId);
}
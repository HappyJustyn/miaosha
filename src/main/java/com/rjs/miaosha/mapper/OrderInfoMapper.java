package com.rjs.miaosha.mapper;

import com.rjs.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.core.annotation.Order;
import tk.mybatis.mapper.common.Mapper;

public interface OrderInfoMapper extends Mapper<OrderInfo> {
    public Long insertOrderInfo(OrderInfo orderInfo);

    @Select(value = "select * from order_info where id=#{id}")
    public OrderInfo getOrderById(@Param("id") Long id);
}
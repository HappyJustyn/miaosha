package com.rjs.miaosha.vo;

import com.rjs.miaosha.model.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 包含商品和秒杀商品信息
 * @Author: Justyn
 * @Date: 2018/6/13 13:16
 */
@Data
public class GoodsVO extends Goods {

    private BigDecimal miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

}

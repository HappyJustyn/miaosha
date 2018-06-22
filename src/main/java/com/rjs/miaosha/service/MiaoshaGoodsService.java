package com.rjs.miaosha.service;

import com.rjs.miaosha.mapper.MiaoshaGoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 12:38
 */
@Service
public class MiaoshaGoodsService {

    @Resource
    private MiaoshaGoodsMapper miaoshaGoodsMapper;

    // 减库存
    public int reduceStockById(Long goodsId) {
        return miaoshaGoodsMapper.reduceStockById(goodsId);
    }
}

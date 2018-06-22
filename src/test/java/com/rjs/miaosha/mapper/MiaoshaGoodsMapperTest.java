package com.rjs.miaosha.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 10:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaGoodsMapperTest {
    @Resource
    private MiaoshaGoodsMapper miaoshaGoodsMapper;

    @Test
    public void reduceStockById() {
        miaoshaGoodsMapper.reduceStockById(2L);
    }
}
package com.rjs.miaosha.service;

import com.rjs.miaosha.mapper.GoodsMapper;
import com.rjs.miaosha.vo.GoodsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/13 13:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {

    @Resource
    private GoodsMapper goodsMapper;

    @Test
    public void getGoodsVO() {
        List<GoodsVO> list = goodsMapper.getGoodsVO();
        for (GoodsVO goodsVO : list) {
            System.out.println(goodsVO);
        }
    }

    @Test
    public void getByGoodsId() {
        Long id = 1L;
        GoodsVO goodsVO = goodsMapper.getByGoodsId(id);
        System.out.println(goodsVO);
    }
}
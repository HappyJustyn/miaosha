package com.rjs.miaosha.service;

import com.rjs.miaosha.mapper.GoodsMapper;
import com.rjs.miaosha.model.Goods;
import com.rjs.miaosha.vo.GoodsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/13 13:32
 */
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    public List<GoodsVO> getGoodsVO() {
        return goodsMapper.getGoodsVO();
    }

    public GoodsVO getByGoodsId(Long goodsId) {
        return goodsMapper.getByGoodsId(goodsId);
    }

    public int reduceStockById(Long id) {
        return goodsMapper.reduceStockById(id);
    }

}

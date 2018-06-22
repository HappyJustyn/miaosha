package com.rjs.miaosha.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.rjs.miaosha.config.KaptchaConfig;
import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.exception.CustomException;
import com.rjs.miaosha.mapper.MiaoshaOrderMapper;
import com.rjs.miaosha.model.MiaoshaOrder;
import com.rjs.miaosha.model.OrderInfo;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.redis.prefix.MiaoshaOrderPrefix;
import com.rjs.miaosha.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 12:20
 */

@Service
public class MiaoshaOrderService {

    @Resource
    private MiaoshaOrderMapper miaoshaOrderMapper;
    @Resource
    private GoodsService goodsService;
    @Resource
    private MiaoshaGoodsService miaoshaGoodsService;
    @Resource
    private OrderInfoService orderInfoService;
    @Resource
    private RedisService redisService;
    @Resource
    private KaptchaConfig kaptchaConfig;


    public MiaoshaOrder getOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        return redisService.get(MiaoshaOrderPrefix.getByUserIdAndGoodsId, "" + userId + "_" + goodsId, MiaoshaOrder.class);
//        return miaoshaOrderMapper.getOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo miaosha(User user, GoodsVO goods) {
        //减库存 下订单 写入秒杀订单
        int i = miaoshaGoodsService.reduceStockById(goods.getId());
        if (i == 0) {
            // 秒杀结束存入标记
            setGoodsOver(goods.getId());
            throw new CustomException(ResultEnum.MIAOSHA_OVER);
        }
        // 下订单
        OrderInfo orderInfo = orderInfoService.createOrder(user, goods);
        redisService.set(MiaoshaOrderPrefix.getByUserIdAndGoodsId, "" + user.getId() + "_" + goods.getId(), orderInfo);
        return orderInfo;
    }

    // 创建订单
    public int createOrder(MiaoshaOrder miaoshaOrder) {
        return miaoshaOrderMapper.insert(miaoshaOrder);
    }

    // 查询订单结果
    public Long getMiaoshaResult(Long userId, Long goodsId) {
        MiaoshaOrder order = getOrderByUserIdAndGoodsId(userId, goodsId);
        if (order != null) {
            return order.getId();
        } else {
            Boolean b = getGoodsOver(goodsId);
            if (b) {
                return -1L;
            } else {
                return 0L;
            }
        }
    }

    public void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaOrderPrefix.isGoodsOver, "" + goodsId, true);
    }

    public Boolean getGoodsOver(Long goodsId) {
        return redisService.exists(MiaoshaOrderPrefix.isGoodsOver, "" + goodsId);
    }

    public Boolean checkPath(User user, Long goodsId, String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        String str = redisService.get(MiaoshaOrderPrefix.getMiaoshaPath, user.getId() + "_" + goodsId, String.class);
        return str.equals(path);
    }

    // 生成验证码图片
    public BufferedImage createVerifyCode(User user, Long goodsId) {
        DefaultKaptcha defaultKaptcha = kaptchaConfig.getDefaultKaptcha();
        String text = generateText();
        redisService.set(MiaoshaOrderPrefix.verifyCode, "" + user.getId() + goodsId, eval(text));
        return defaultKaptcha.createImage(text);
    }

    // 生成验证码文本
    public String generateText() {
        char opt[] = new char[]{'+', '-', '*'};
        Random random = new Random();
        int num1 = random.nextInt(9) + 1;
        int num2 = random.nextInt(9) + 1;
        int num3 = random.nextInt(9) + 1;
        char opt1 = opt[random.nextInt(3)];
        char opt2 = opt[random.nextInt(3)];
        return "" + num1 + opt1 + num2 + opt2 + num3;
    }

    public Integer eval(String expression) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            return (Integer) engine.eval(expression);
        } catch (ScriptException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 验证验证码
    public boolean checkVerifyCode(User user, Long goodsId, Integer verifyCode) {
        Integer result = redisService.get(MiaoshaOrderPrefix.verifyCode, "" + user.getId() + goodsId, Integer.class);
        if (result == verifyCode) {
            return true;
        }
        return false;
    }

    public boolean checkAccessTimes(String url, MiaoshaOrderPrefix prefix, Integer maxCount) {
        Integer count = redisService.get(prefix, url, Integer.class);
        if (count == null) {
            redisService.set(prefix, url, 1);
        } else if (count < maxCount) {
            redisService.incr(prefix, url);
        } else {
            return false;
        }
        return true;
    }
}

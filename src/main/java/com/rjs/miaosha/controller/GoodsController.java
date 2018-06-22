package com.rjs.miaosha.controller;

import com.rjs.miaosha.model.User;
import com.rjs.miaosha.redis.prefix.GoodsPrefix;
import com.rjs.miaosha.service.GoodsService;
import com.rjs.miaosha.service.RedisService;
import com.rjs.miaosha.service.UserService;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.GoodsDetailVO;
import com.rjs.miaosha.vo.GoodsVO;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/12 20:36
 */
@RequestMapping(value = "/goods")
@Controller
public class GoodsController {

    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private RedisService redisService;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/toGoodsList")
    @ResponseBody
    public String toGoodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);
        // 从缓存中取数据
        String html = redisService.get(GoodsPrefix.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        // 手动渲染
        List<GoodsVO> list = goodsService.getGoodsVO();
        model.addAttribute("goodsList", list);
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        // 存入缓存，方便下次使用
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsPrefix.goodsList, "", html);
        }
        return html;
    }

//    @RequestMapping(value = "/to_detail/{goodsId}")
//    @ResponseBody
//    public String toDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "goodsId") Long goodsId, Model model, User user) {
//        model.addAttribute("user", user);
//
//        // 从缓存中取数据
//        String html = redisService.get(GoodsPrefix.goodsDetail, "" + goodsId, String.class);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//        // 手动渲染
//        GoodsVO goodsVO = goodsService.getByGoodsId(goodsId);
//        Long startTime = goodsVO.getStartDate().getTime();
//        Long endTime = goodsVO.getEndDate().getTime();
//        Long nowTime = System.currentTimeMillis();
//        int remainSeconds = 0; // 秒杀状态 0-未开始 1-进行中 2-已结束
//        int miaoshaStatus = 0; // 秒杀状态
//        if (nowTime < startTime) { // 秒杀未开始
//            remainSeconds = (int) (startTime - nowTime) / 1000;
//        } else if (nowTime > endTime) {
//            remainSeconds = -1;
//            miaoshaStatus = 2;
//        } else {
//            remainSeconds = 0;
//            miaoshaStatus = 1;
//        }
//        model.addAttribute("miaoshaStatus", miaoshaStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("goods", goodsVO);
//        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
//        // 存入缓存，方便下次使用
//        if (!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsPrefix.goodsDetail, "" + goodsId, html);
//        }
//        return html;
//    }

    @GetMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public ResultVO toDetail(@PathVariable(value = "goodsId") Long goodsId, User user) {
        GoodsVO goodsVO = goodsService.getByGoodsId(goodsId);
        Long startTime = goodsVO.getStartDate().getTime();
        Long endTime = goodsVO.getEndDate().getTime();
        Long nowTime = System.currentTimeMillis();
        int remainSeconds = 0; // 秒杀状态 0-未开始 1-进行中 2-已结束
        int miaoshaStatus = 0; // 秒杀状态
        if (nowTime < startTime) { // 秒杀未开始
            remainSeconds = (int) (startTime - nowTime) / 1000;
        } else if (nowTime > endTime) {
            remainSeconds = -1;
            miaoshaStatus = 2;
        } else {
            remainSeconds = 0;
            miaoshaStatus = 1;
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setRemainSeconds(remainSeconds);
        goodsDetailVO.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVO.setGoodsVO(goodsVO);
        goodsDetailVO.setUser(user);
        return ResultUtil.success(goodsDetailVO);
    }
}

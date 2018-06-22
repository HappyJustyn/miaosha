package com.rjs.miaosha.controller;

import com.rjs.miaosha.annotation.AccessLimit;
import com.rjs.miaosha.dto.MiaoshaMessageDTO;
import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.exception.CustomException;
import com.rjs.miaosha.model.MiaoshaOrder;
import com.rjs.miaosha.model.OrderInfo;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.rabbitmq.MQSender;
import com.rjs.miaosha.redis.prefix.GoodsPrefix;
import com.rjs.miaosha.redis.prefix.MiaoshaOrderPrefix;
import com.rjs.miaosha.service.GoodsService;
import com.rjs.miaosha.service.MiaoshaOrderService;
import com.rjs.miaosha.service.RedisService;
import com.rjs.miaosha.util.EncyptionUtil;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.util.UUIDUtil;
import com.rjs.miaosha.vo.GoodsVO;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 10:48
 */
@Controller
@RequestMapping(value = "/miaosha")
public class MiaoshaController implements InitializingBean {

    // 商品结束秒杀标记
    private Map<Long, Boolean> localOverMap = new HashMap<>();

    @Resource
    private GoodsService goodsService;
    @Resource
    private MiaoshaOrderService miaoshaOrderService;
    @Resource
    private RedisService redisService;
    @Resource
    private MQSender mqSender;


    /*
    系统初始化时，进行一些操作
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> list = goodsService.getGoodsVO();
        if (list == null) {
            return;
        }
        // 系统启动时候加载商品数量到缓存
        for (GoodsVO goodsVO : list) {
            redisService.set(GoodsPrefix.goodsMiaoshaStock, "" + goodsVO.getId(), goodsVO.getStockCount());
            localOverMap.put(goodsVO.getId(), false);
        }
    }

    @PostMapping(value = "/{path}/do_miaosha")
    @ResponseBody
    public ResultVO doMiaosha(@RequestParam(value = "goodsId") Long goodsId, User user, @PathVariable("path") String path) {
        if (user == null) {
            return ResultUtil.error(ResultEnum.SESSION_OVERDUE);
        }
        // 验证path，没有则非法请求
        Boolean checkResult = miaoshaOrderService.checkPath(user, goodsId, path);
        if (!checkResult) {
            return ResultUtil.error(ResultEnum.REQUEST_ILLEGAL);
        }
        // 秒杀结束，直接返回失败，减少redis访问
        Boolean over = localOverMap.get(goodsId);
        if (over) {
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            throw new CustomException(ResultEnum.MIAOSHA_DONE);
        }

        // 预减库存库存减一，返回剩余库存
        Long stock = redisService.decr(GoodsPrefix.goodsMiaoshaStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }

        // 入队，通过队列把同步请求变为异步请求，减少等待时间
        MiaoshaMessageDTO miaoshaMessageDTO = new MiaoshaMessageDTO();
        miaoshaMessageDTO.setGoodsId(goodsId);
        miaoshaMessageDTO.setUser(user);
        mqSender.sendMessage(miaoshaMessageDTO);

        return ResultUtil.success("排队中");

        /*
        //判断库存
        GoodsVO goods = goodsService.getByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return ResultUtil.error(ResultEnum.MIAOSHA_DONE);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaOrderService.miaosha(user, goods);
        return ResultUtil.success(orderInfo);
        */
    }

    /**
     * @Description: 轮询查询秒杀结果
     * @Param:
     * @Return: 成功：orderId 失败：-1 排队中：0
     * @Author: Justyn
     * @Date: 2018/6/21 9:23
     */
    @GetMapping(value = "/result")
    @ResponseBody
    public ResultVO result(@RequestParam(value = "goodsId") Long goodsId, User user) {
        if (user == null) {
            return ResultUtil.error(ResultEnum.SESSION_OVERDUE);
        }
        Long result = miaoshaOrderService.getMiaoshaResult(user.getId(), goodsId);
        return ResultUtil.success(result);
    }

    /**
     * @Description: 隐藏秒杀接口地址，从接口中获取
     * @Param:
     * @Return:
     * @Author: Justyn
     * @Date: 2018/6/21 17:46
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping(value = "/path")
    @ResponseBody
    public ResultVO path(HttpServletRequest request,
                         @RequestParam(value = "goodsId") Long goodsId,
                         @RequestParam(value = "verifyCode") Integer verifyCode,
                         User user) {
        if (user == null) {
            return ResultUtil.error(ResultEnum.SESSION_OVERDUE);
        }

        // 限制固定时间内访问次数 !!!写入拦截器了
//        String url = request.getRequestURI() + "_" + user.getId();
//        boolean access = miaoshaOrderService.checkAccessTimes(url);
//        if (!access) {
//            throw new CustomException(ResultEnum.REQUEST_FREQUENTLY);
//        }

        // 核对验证码
        if (verifyCode == null) {
            throw new CustomException(ResultEnum.VERIFYCODE_NULL);
        }
        boolean check = miaoshaOrderService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            throw new CustomException(ResultEnum.VERIFYCODE_ERROR);
        }
        // path标志字段，请求有此字段说明是从path接口获取地址，没有则是非法请求秒杀地址
        String str = EncyptionUtil.md5(UUIDUtil.getUUID() + "123456");
        redisService.set(MiaoshaOrderPrefix.getMiaoshaPath, user.getId() + "_" + goodsId, str);
        return ResultUtil.success(str);
    }

    /**
     * @Description: 验证码
     * @Param:
     * @Return:
     * @Author: Justyn
     * @Date: 2018/6/22 14:33
     */
    @GetMapping(value = "/verifyCode")
    @ResponseBody
    public void verifyCode(HttpServletResponse response, User user, @RequestParam("goodsId") long goodsId) {
        BufferedImage image = miaoshaOrderService.createVerifyCode(user, goodsId);
        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

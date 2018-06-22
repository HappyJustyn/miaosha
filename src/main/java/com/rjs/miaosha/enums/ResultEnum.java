package com.rjs.miaosha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/9 0:14
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    SERVER_ERROR(500100, "服务端异常"),
    PARAM_NULL(500101, "参数为空"),
    REQUEST_ILLEGAL(500102, "请求非法"),
    USER_NULL(500103, "未登录"),
    // 登录模块5002xx
    LOGIN_SUCCESS(500200, "登录成功"),
    PARAM_ERROR(500201, "参数错误"),
    USER_NOT_EXIST(500202, "用户不存在"),
    PASSWORD_ERROR(500203, "密码错误"),
    SESSION_OVERDUE(500204, "session失效"),
    // 商品模块5003xx

    // 订单模块5004xx
    ORDER_NOT_EXIST(500401, "订单不存在"),

    // 秒杀模块5005xx
    MIAOSHA_OVER(500501, "秒杀结束"),
    MIAOSHA_DONE(500502, "已秒杀"),
    VERIFYCODE_ERROR(500503, "验证码错误"),
    VERIFYCODE_NULL(500503, "验证码为空"),
    REQUEST_FREQUENTLY(500504, "访问过于频繁"),
    ;


    private Integer code;

    private String message;
}

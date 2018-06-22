package com.rjs.miaosha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/15 13:30
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    CREATE_NOT_PAY(0, "新建未支付"),
    PAYED(1, "已支付"),
    ;

    private Integer code;
    private String message;
}

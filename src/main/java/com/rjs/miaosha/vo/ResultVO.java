package com.rjs.miaosha.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Description: 访问结果返回类
 * @Auther: Justyn
 * @Date: 2018/6/7 23:03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultVO<T> {

    // 0-成功 1-失败
    private Integer code;

    private String message;

    private T data;
}

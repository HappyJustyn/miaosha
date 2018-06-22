package com.rjs.miaosha.exception;

import com.rjs.miaosha.enums.ResultEnum;
import lombok.Data;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/11 19:28
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.message = resultEnum.getMessage();
        this.code = resultEnum.getCode();
    }
}

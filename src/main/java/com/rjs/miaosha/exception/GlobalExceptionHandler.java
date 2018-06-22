package com.rjs.miaosha.exception;

import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/12 12:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultVO exceptionHandler(Exception e) {
        if (e instanceof CustomException) {
            e.printStackTrace();
            CustomException customException = (CustomException) e;
            return ResultUtil.error(customException.getCode(), customException.getMessage());
        } else {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.SERVER_ERROR);
        }
    }
}

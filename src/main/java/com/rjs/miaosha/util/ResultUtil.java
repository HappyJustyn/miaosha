package com.rjs.miaosha.util;

import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.vo.ResultVO;

import java.util.List;

/**
 * @Description: Result类的工具类
 * @Author: Justyn
 * @Date: 2018/6/9 0:01
 */
public class ResultUtil {

    // 访问成功调用
    public static ResultVO success(Object data) {
        ResultVO result = new ResultVO();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static ResultVO success() {
        return success(null);
    }

    // 访问失败调用
    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO result = new ResultVO();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

}

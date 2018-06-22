package com.rjs.miaosha.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/11 19:21
 */
@Data
public class LoginVO {

    @NotEmpty(message = "手机号码不能为空")
    private String mobile;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 32, max = 32)
    private String password;
}

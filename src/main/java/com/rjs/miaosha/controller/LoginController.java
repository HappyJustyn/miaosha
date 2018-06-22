package com.rjs.miaosha.controller;

import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.service.UserService;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.LoginVO;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/11 19:06
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public ResultVO dologin(@Valid LoginVO loginVO, HttpServletResponse response) {
        ResultEnum resultEnum = userService.login(loginVO, response);
        return ResultUtil.success(resultEnum);
    }
}

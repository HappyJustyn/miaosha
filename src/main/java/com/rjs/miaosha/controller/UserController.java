package com.rjs.miaosha.controller;

import com.rjs.miaosha.model.User;
import com.rjs.miaosha.service.RedisService;
import com.rjs.miaosha.service.UserService;
import com.rjs.miaosha.util.ResultUtil;
import com.rjs.miaosha.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private RedisService redisService;
	
    @RequestMapping("/info")
    @ResponseBody
    public ResultVO<User> info(Model model, User user) {
        return ResultUtil.success(user);
    }
    
}

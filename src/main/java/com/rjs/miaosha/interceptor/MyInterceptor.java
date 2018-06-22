package com.rjs.miaosha.interceptor;

import com.alibaba.fastjson.JSON;
import com.rjs.miaosha.annotation.AccessLimit;
import com.rjs.miaosha.config.CookieProperties;
import com.rjs.miaosha.config.UserContext;
import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.redis.prefix.MiaoshaOrderPrefix;
import com.rjs.miaosha.service.MiaoshaOrderService;
import com.rjs.miaosha.service.UserService;
import com.rjs.miaosha.util.ResultUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 8:16
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;
    @Resource
    private MiaoshaOrderService miaoshaOrderService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 对AccessLimit注解进行处理
        boolean b = handler instanceof HandlerMethod;
        if (handler instanceof HandlerMethod) {
            User user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin) {
                if(user == null) {
                    render(response, ResultEnum.USER_NULL);
                    return false;
                }
                key += "_" + user.getId();
            }else {
                //do nothing
            }
            MiaoshaOrderPrefix prefix = MiaoshaOrderPrefix.accessWithExpire(seconds);
            // 限制固定时间内访问次数
            boolean access = miaoshaOrderService.checkAccessTimes(key, prefix, maxCount);
            if (!access) {
                render(response, ResultEnum.REQUEST_FREQUENTLY);
            }
        }
        return true;
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = getCookieValue(request, CookieProperties.COOKIE_NAME);
        if(StringUtils.isEmpty(cookieToken)) {
            return null;
        }
        User user = userService.getByToken(response, cookieToken);
        return user;
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void render(HttpServletResponse response, ResultEnum resultEnum)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(ResultUtil.error(resultEnum));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}

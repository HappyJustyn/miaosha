package com.rjs.miaosha.service;

import com.rjs.miaosha.config.CookieProperties;
import com.rjs.miaosha.enums.ResultEnum;
import com.rjs.miaosha.exception.CustomException;
import com.rjs.miaosha.mapper.UserMapper;
import com.rjs.miaosha.model.User;
import com.rjs.miaosha.redis.prefix.UserPrefix;
import com.rjs.miaosha.util.EncyptionUtil;
import com.rjs.miaosha.util.UUIDUtil;
import com.rjs.miaosha.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/11 10:28
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisService redisService;

    public User getOneByName(String username) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("nickname", username);
        return userMapper.selectOneByExample(example);
    }

    public User getById(Long id) {
        User user = redisService.get(UserPrefix.getById, id.toString(), User.class);
        if (user != null) {
            return user;
        }
        user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            redisService.set(UserPrefix.getById, id.toString(), user);
        }
        return user;
    }

    // 更新密码
    @Transactional
    public void updateUserPassword(Long id, String password, String token) {
        User user = getById(id);
        if (user == null) {
            throw new CustomException(ResultEnum.USER_NOT_EXIST);
        }
        User userNew = new User();
        userNew.setId(id);
        userNew.setPassword(EncyptionUtil.md5WithSalt(password, user.getSalt()));
        // 更新数据库
        userMapper.updateByPrimaryKeySelective(user);
        // 处理缓存
        redisService.delete(UserPrefix.getById, "" + id);
        user.setPassword(userNew.getPassword());
        redisService.set(UserPrefix.getByCookie, token, user);
    }

    /**
     * @Description: 登录实现
     * @Param:
     * @Return:
     * @Author: Justyn
     * @Date: 2018/6/11 19:36
     */
    public ResultEnum login(LoginVO loginVO, HttpServletResponse response) {
        log.info("【登录认证】用户信息：{}", loginVO.toString());
        if (loginVO == null) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }
        String mobile = loginVO.getMobile();
        User user = userMapper.selectByPrimaryKey(mobile);
        if (user == null) {
            throw new CustomException(ResultEnum.USER_NOT_EXIST);
        }
        String salt = user.getSalt();
        String loginPwd = EncyptionUtil.md5WithSalt(loginVO.getPassword(), salt);
        if (!loginPwd.equals(user.getPassword())) {
            throw new CustomException(ResultEnum.PASSWORD_ERROR);
        }
        // 登录成功，生成cookie
        String token = UUIDUtil.getUUID();
        addUpdateSession(response, token, user);
        return ResultEnum.LOGIN_SUCCESS;
    }

    // 第一次访问增加sesseion，后续访问更新session（更新就是重新生成session，以保持session的存活时间）
    private void addUpdateSession(HttpServletResponse response, String token, User user) {
        Cookie cookie = new Cookie(CookieProperties.COOKIE_NAME, token);
        cookie.setMaxAge(UserPrefix.getByCookie.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        // 保存用户信息到redis
        redisService.set(UserPrefix.getByCookie, token, user);
    }

    // 根据cookie从redis中取用户信息
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            throw new CustomException(ResultEnum.PARAM_NULL);
        }
        User user = redisService.get(UserPrefix.getByCookie, token, User.class);
        if (user != null) {
            addUpdateSession(response, token, user);
        }
        return user;
    }
}

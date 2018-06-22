package com.rjs.miaosha.dto;

import com.rjs.miaosha.model.User;
import lombok.Data;

/**
 * @Description: 入队数据包装类
 * @Author: Justyn
 * @Date: 2018/6/21 8:31
 */
@Data
public class MiaoshaMessageDTO {
    private User user;
    private Long goodsId;
}

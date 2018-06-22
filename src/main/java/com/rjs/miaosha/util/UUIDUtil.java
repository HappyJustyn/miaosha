package com.rjs.miaosha.util;

import java.util.UUID;

/**
 * @Description: uuid相关工具类
 * @Auther: Justyn
 * @Date: 2018/6/1 16:45
 */
public class UUIDUtil {

    public static void main(String[] args) {
    }

    /**
     * @Description: 返回一个uuid
     * @Return: uuid
     * @Author: Justyn
     * @Date: 2018/6/1 16:51
     */
    public static String getUUID() {
        UUID uid = UUID.randomUUID();
        return uid.toString().replace("-","");
    }
}

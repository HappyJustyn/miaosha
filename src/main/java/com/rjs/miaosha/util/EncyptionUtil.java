package com.rjs.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/11 18:46
 */
public class EncyptionUtil {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String md5WithSalt(String pwd, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + pwd + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }


//    public static void main(String[] args) {
//        System.out.println(md5WithSalt("123456", "1a2b3c4d"));
//        System.out.println(md5WithSalt(md5WithSalt("123456", "1a2b3c4d"), "1a2b3c4d"));
//    }
}

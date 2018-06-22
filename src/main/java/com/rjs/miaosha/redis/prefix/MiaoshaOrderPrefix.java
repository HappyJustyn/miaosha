package com.rjs.miaosha.redis.prefix;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/20 9:14
 */
public class MiaoshaOrderPrefix extends BasePrefix {

    private Integer expireSeconds;
    private String prefix;

    public MiaoshaOrderPrefix(String prefix) {
        super(prefix);
    }

    public MiaoshaOrderPrefix(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaOrderPrefix accessWithExpire(Integer expireSeconds) {
        return new MiaoshaOrderPrefix(expireSeconds, "access");
    }

    public static MiaoshaOrderPrefix getByUserIdAndGoodsId = new MiaoshaOrderPrefix("uidgid");
    public static MiaoshaOrderPrefix isGoodsOver = new MiaoshaOrderPrefix("goodsOver");
    public static MiaoshaOrderPrefix getMiaoshaPath = new MiaoshaOrderPrefix(60, "miaoPath");
    public static MiaoshaOrderPrefix verifyCode = new MiaoshaOrderPrefix(60, "verifyCode");

}

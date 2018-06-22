package com.rjs.miaosha.util;

import com.rjs.miaosha.model.User;
import com.rjs.miaosha.redis.prefix.UserPrefix;
import com.rjs.miaosha.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/18 19:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUtil {

    @Resource
    private RedisService redisService;

    // 生成user数据并存入redis
    @Test
    public void generateUser() throws IOException {

        File sqlFile = new File("d:/sql.txt");
        if (!sqlFile.exists()) {
            sqlFile.createNewFile();
        }
        FileWriter sqlWriter = new FileWriter(sqlFile, true);
        FileWriter tokenWriter = new FileWriter("d:/token.txt", true);

        for (Long i = 0L; i < 5000; i++) {
            User user = new User();
            user.setId(18862005633L + i);
            user.setPassword("b7797cce01b4b131b433b6acf4add449");
            user.setNickname("tom" + i);
            user.setSalt("1a2b3c4d");

            String sqlStr = "INSERT INTO miaosha_user(id,nickname,password,salt) VALUES("
                    + user.getId() + ",'" + user.getNickname() + "','" + user.getPassword() + "','" + user.getSalt() + "');" + "\r\n";
            sqlWriter.write(sqlStr);

            String token = UUIDUtil.getUUID();
            String tokenStr = user.getId() + "," + token + "\r\n";
            tokenWriter.write(tokenStr);
            redisService.set(UserPrefix.getByCookie, token, user);
        }
        sqlWriter.flush();
        sqlWriter.close();
        tokenWriter.flush();
        tokenWriter.close();

    }
}

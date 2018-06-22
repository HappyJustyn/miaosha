package com.rjs.miaosha.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: Justyn
 * @Date: 2018/6/22 14:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaOrderServiceTest {
    @Resource
    private MiaoshaOrderService miaoshaOrderService;

    @Test
    public void createVerifyCode() {
//        System.out.println(miaoshaOrderService.createVerifyCode());
    }

    @Test
    public void generateText() {
        String text = miaoshaOrderService.generateText();
        System.out.println(text);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            System.out.println((Integer) engine.eval(text));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
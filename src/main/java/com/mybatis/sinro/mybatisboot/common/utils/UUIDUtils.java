package com.mybatis.sinro.mybatisboot.common.utils;/*
 * @ClassName UuidKit
 * @Auther: WangJinbo
 * @Date: 2018/8/11 16:27
 * @Description:
 **/

import java.util.Random;
import java.util.UUID;

/**
 * @author wxjason
 */
public class UUIDUtils {
    public static String create() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String random(int length) {
        //获取包含26个字母大小写和数字的字符数组
        char[] c= charArray();
        return random(length, c);
    }

    public static String randomNumber(int length) {
        //获取数字的字符数组
        char[] c= charArrayNumber();
        return random(length, c);
    }

    private static String random(int length, char[] c){
        Random rd = new Random();
        StringBuilder code= new StringBuilder();
        for (int k = 0; k < length; k++) {
            //随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            //循环添加到字符串后面
            code.append(c[index]);
        }
        return code.toString();
    }

    private static char[] charArray(){
        String num = "1234567890";
        String lowerCase ="qwertyuiopasdfghjklzxcvbnm";
        String upperCase = lowerCase.toUpperCase();
        String word = lowerCase + upperCase + num;
        return word.toCharArray();
    }
    private static char[] charArrayNumber(){
        return "1234567890".toCharArray();
    }

}

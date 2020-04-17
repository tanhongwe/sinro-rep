package com.mybatis.sinro.mybatisboot.test;

/**
 * @author: tan.hongwei
 * @Date: 2020/4/9 10:58
 * @Description:
 */
public class Person {
    private String name = "tom";
    private int age;
    private final double salary = 99;
    private static String adress;
    private final static String hoby = "kkk";

    public void say(){
        System.out.println("person say");
    }

    public static int calc(int op11,int op22){
        op11 = 3;
        int result = op11 + op22;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(calc(1,2));
    }
}

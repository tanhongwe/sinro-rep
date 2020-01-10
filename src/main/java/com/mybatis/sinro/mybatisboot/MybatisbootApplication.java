package com.mybatis.sinro.mybatisboot;

import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.EnableEncryptBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tan.hongwei
 */
@SpringBootApplication
@EnableEncryptBody
public class MybatisbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisbootApplication.class, args);
    }

}

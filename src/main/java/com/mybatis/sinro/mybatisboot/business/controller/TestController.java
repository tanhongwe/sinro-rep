package com.mybatis.sinro.mybatisboot.business.controller;

import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tan.hongwei
 * @Date: 2020/4/13 09:51
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/getIp")
    public String getIpAndPort(){
        String ip = HttpUtils.getIp();
        int port = HttpUtils.getLocalPort();
        String value = ip + ":" + port;
        return value;
    }
}

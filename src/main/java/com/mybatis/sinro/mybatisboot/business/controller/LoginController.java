package com.mybatis.sinro.mybatisboot.business.controller;

import com.mybatis.sinro.mybatisboot.system.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:37
 * @Description:
 */
@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final IUserService userService;

    @GetMapping("/index")
    @ResponseBody
    public String login (){
        return "Hello World ~";
    }
}

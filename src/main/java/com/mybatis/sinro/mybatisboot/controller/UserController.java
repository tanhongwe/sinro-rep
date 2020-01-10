package com.mybatis.sinro.mybatisboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.AESDecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.encrypt.AESEncryptBody;
import com.mybatis.sinro.mybatisboot.common.excel.utils.ExcelDownLoadUtil;
import com.mybatis.sinro.mybatisboot.common.rsa.Result;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.entity.User;
import com.mybatis.sinro.mybatisboot.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:37
 * @Description:
 */
@RestController
@AESEncryptBody
@AESDecryptBody
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @GetMapping("/list/page")
    public String pagelist(Page page){
        return Result.success(userService.page(page));
    }

    @GetMapping("/list")
    public String list(){
        return Result.success(userService.list());
    }

    @PostMapping("/add")
    public String add(User user){
        return Result.success(userService.save(user));
    }

    @GetMapping("/ip")
    public String getIpAndPort(){
        return HttpUtils.getIp() + ":" + HttpUtils.getPort();
    }

    @GetMapping("/down")
    public void downLoad(HttpServletResponse res){
        ExcelDownLoadUtil.simpleDown(userService.list(),new String[]{"用户名","密码","电话号码"},res);
    }
}

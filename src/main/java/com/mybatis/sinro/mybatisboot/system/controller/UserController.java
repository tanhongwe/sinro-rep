package com.mybatis.sinro.mybatisboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mybatis.sinro.mybatisboot.common.bean.Result;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.AESDecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.encrypt.AESEncryptBody;
import com.mybatis.sinro.mybatisboot.common.excel.utils.ExcelDownLoadUtil;
import com.mybatis.sinro.mybatisboot.common.factory.PageFactory;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.controller.BaseController;
import com.mybatis.sinro.mybatisboot.system.bean.UserAo;
import com.mybatis.sinro.mybatisboot.system.bean.UserVo;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import com.mybatis.sinro.mybatisboot.system.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
public class UserController extends BaseController {

    private final IUserService userService;

    @PostMapping("/list/page")
    public String listPage(@RequestBody UserAo userAo){
        IPage page = new PageFactory<User>().defaultPage();
        return success(userService.page(page,UserAo.pageCondition(userAo)),UserVo.class);
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
        return HttpUtils.getIp() + ":" + HttpUtils.getLocalPort();
    }

    @GetMapping("/down")
    public void downLoad(HttpServletResponse res){
        ExcelDownLoadUtil.simpleDown(userService.list(),new String[]{"用户名","密码","电话号码"},res);
    }

    @GetMapping("/current")
    public String current(){
        User user = userService.getUserById(HttpUtils.getUserId());
        return success(user, UserVo.class);
    }
}

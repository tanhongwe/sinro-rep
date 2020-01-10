package com.mybatis.sinro.mybatisboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.sinro.mybatisboot.entity.User;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:50
 * @Description:
 */
public interface IUserService extends IService<User> {

    /**
     *
     * @return
     */
    IPage<User> page();
}

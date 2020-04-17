package com.mybatis.sinro.mybatisboot.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.sinro.mybatisboot.system.entity.User;

import java.util.List;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:50
 * @Description:
 */
public interface IUserService extends IService<User> {



    /**
     * 根据用户名查询用户
     * @param username
     * @return User
     */
    User getByUsername(String username);

    /**
     * 根据用户ID查询用户
     * @param userId
     * @return User
     */
    User getUserById(String userId);

    /**
     * 根据角色ID查询用户列表
     * @param roleId
     * @return List<User>
     */
    List<User> selectByRoleId(String roleId);
}

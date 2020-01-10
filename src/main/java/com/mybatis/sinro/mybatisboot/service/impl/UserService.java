package com.mybatis.sinro.mybatisboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.sinro.mybatisboot.entity.User;
import com.mybatis.sinro.mybatisboot.mapper.IUserMapper;
import com.mybatis.sinro.mybatisboot.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:51
 * @Description:
 */
@Service
public class UserService extends ServiceImpl<IUserMapper,User> implements IUserService {


    @Override
    public IPage<User> page() {
        IPage<User> page = new Page<>();
        return this.baseMapper.selectPage(page,new QueryWrapper<>());
    }
}

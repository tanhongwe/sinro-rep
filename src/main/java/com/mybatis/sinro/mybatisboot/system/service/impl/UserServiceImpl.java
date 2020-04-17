package com.mybatis.sinro.mybatisboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.sinro.mybatisboot.common.enumeration.Delete;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.system.common.Constants;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import com.mybatis.sinro.mybatisboot.system.mapper.UserMapper;
import com.mybatis.sinro.mybatisboot.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:51
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Override
    public User getByUsername(String loginName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.USERNAME,loginName);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public User getUserById(String userId) {
        if (!Constants.ADMIN_ROLE_ID.equals(HttpUtils.getUserId()) && Constants.ADMIN_ROLE_ID.equals(userId)) {
            throw new SinroException(ExceptionType.USER_ADMIN_NOT_UPDATE);
        }
        if (StringUtils.isBlank(userId)) {
            throw new SinroException(ExceptionType.USER_ID_EMPTY);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.ID, userId)
                .eq(User.DEL, Delete.NORMOL.getCode());
        User user = this.getOne(wrapper);
        if (Objects.isNull(user)) {
            throw new SinroException(ExceptionType.USER_ID_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public List<User> selectByRoleId(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SinroException(ExceptionType.ROLE_ID_EMPTY);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne(User.ID, Constants.ADMIN_ROLE_ID);
        wrapper.eq(User.ROLE_ID, roleId);
        return this.list(wrapper);
    }
}

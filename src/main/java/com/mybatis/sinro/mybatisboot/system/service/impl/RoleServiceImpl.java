package com.mybatis.sinro.mybatisboot.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.CollectionUtils;
import com.mybatis.sinro.mybatisboot.common.utils.DateUtils;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.common.utils.RegexUtils;
import com.mybatis.sinro.mybatisboot.system.bean.RoleAo;
import com.mybatis.sinro.mybatisboot.system.common.Constants;
import com.mybatis.sinro.mybatisboot.system.entity.Role;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import com.mybatis.sinro.mybatisboot.system.mapper.RoleMapper;
import com.mybatis.sinro.mybatisboot.system.service.IRoleService;
import com.mybatis.sinro.mybatisboot.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserService userService;

    @Override
    public List<Role> listByPage(Page<Role> page, RoleAo roleAo) {
        return this.baseMapper.findListByPage(page, roleAo);
    }

    @Override
    public List<Role> list(RoleAo roleAo) {
        return this.baseMapper.findList(roleAo);
    }

    @Override
    public String addRole(RoleAo roleAo) {
        roleAo.setRoleId(null);
        this.checkRole(roleAo, true);
        Role role = new Role();
        role.setId(roleAo.getRoleId());
        role.setName(roleAo.getRoleName());
        role.setRemark(roleAo.getRemark());
        role.setCreateUserId(HttpUtils.getUserId());
        role.setCreateTime(DateUtils.localDateTime());
        this.save(role);
        return role.getId();
    }

    @Override
    public void updateRole(RoleAo roleAo) {
        this.checkAdmin(roleAo.getRoleId());
        this.checkRole(roleAo, false);
        Role role = new Role();
        role.setId(roleAo.getRoleId());
        role.setName(roleAo.getRoleName());
        role.setRemark(roleAo.getRemark());
        role.setUpdateUserId(HttpUtils.getUserId());
        role.setUpdateTime(DateUtils.localDateTime());
        int row = this.baseMapper.updateById(role);
        if (row == NumberConstants.ZERO) {
            throw new SinroException(ExceptionType.ROLE_ID_NOT_EXISTS);
        }
    }

    @Override
    public void checkRole(RoleAo roleAo, boolean isAdd) {
        if (!isAdd && StringUtils.isBlank(roleAo.getRoleId())) {
            throw new SinroException(ExceptionType.ROLE_ID_EMPTY);
        }
        if (StringUtils.isBlank(roleAo.getRoleName())) {
            throw new SinroException(ExceptionType.ROLE_NAME_EMPTY);
        }
        if (!RegexUtils.matchName(roleAo.getRoleName())) {
            throw new SinroException(ExceptionType.ROLE_NAME_ERROR);
        }
        Role role = this.getRoleByRoleName(roleAo.getRoleName());
        if (Objects.nonNull(role) && !role.getId().equals(roleAo.getRoleId())) {
            throw new SinroException(ExceptionType.ROLE_NAME_IS_EXISTS);
        }
        if (Objects.nonNull(roleAo.getRemark()) && roleAo.getRemark().length() > NumberConstants.ONE_HUNDRED) {
            throw new SinroException(ExceptionType.ROLE_REMARK_LENGTH_LIMIT);
        }
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(Role.NAME, roleName);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SinroException(ExceptionType.ROLE_ID_EMPTY);
        }
        this.checkAdmin(roleId);
        List<User> userList = userService.selectByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(userList)) {
            throw new SinroException(ExceptionType.ROLE_HAS_USERS);
        }
        int row = this.baseMapper.deleteById(roleId);
        if (row == NumberConstants.ZERO) {
            throw new SinroException(ExceptionType.ROLE_ID_NOT_EXISTS);
        }
    }

    @Override
    public Role getRoleByRoleId(String roleId) {
        Role role = this.getById(roleId);
        if (Objects.isNull(role)) {
            throw new SinroException(ExceptionType.ROLE_ID_NOT_EXISTS);
        }
        return role;
    }

    @Override
    public List<Role> selectListByRoleIds(List<String> roleIds) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.in(Role.ID, roleIds);
        return this.list(wrapper);
    }

    @Override
    public void checkAdmin(String roleId) {
        if (Constants.ADMIN_ROLE_ID.equals(roleId)) {
            throw new SinroException(ExceptionType.ROLE_ADMIN_NOT_UPDATE);
        }
    }
}

package com.mybatis.sinro.mybatisboot.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.CollectionUtils;
import com.mybatis.sinro.mybatisboot.system.bean.RoleMenusAo;
import com.mybatis.sinro.mybatisboot.system.entity.Menu;
import com.mybatis.sinro.mybatisboot.system.entity.RoleMenu;
import com.mybatis.sinro.mybatisboot.system.mapper.RoleMenuMapper;
import com.mybatis.sinro.mybatisboot.system.service.IMenuService;
import com.mybatis.sinro.mybatisboot.system.service.IRoleMenuService;
import com.mybatis.sinro.mybatisboot.system.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单角色关联表 服务实现类
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleBindMenus(RoleMenusAo roleMenusAo) {
        final String roleId = roleMenusAo.getRoleId();
        roleService.checkAdmin(roleMenusAo.getRoleId());
        List<Integer> menuIds = roleMenusAo.getMenuIds();
        // 判断传入菜单ID是否有相同
        if (menuIds.size() != menuIds.stream().distinct().count()) {
            throw new SinroException(ExceptionType.MENU_ID_HAS_SAME);
        }
        if (CollectionUtils.isEmpty(menuIds)) {
            this.deleteByRoleId(roleId);
            return;
        }
        List<Menu> menuList = menuService.selectByMenuIds(menuIds);
        // 判断传入菜单ID是否存在
        if (menuIds.size() != menuList.size()) {
            throw new SinroException(ExceptionType.MENU_ID_NOT_EXISTS);
        }
        final List<RoleMenu> roleMenuList = new ArrayList<>(menuIds.size());
        menuIds.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        });
        this.deleteByRoleId(roleId);
        if (roleMenuList.size() > NumberConstants.ZERO) {
            this.saveBatch(roleMenuList, roleMenuList.size());
        }
    }

    @Override
    public void deleteByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new SinroException(ExceptionType.ROLE_ID_EMPTY);
        }
        roleService.checkAdmin(roleId);
        // 判断角色ID是否存在
        roleService.getRoleByRoleId(roleId);
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq(RoleMenu.ROLE_ID, roleId);
        this.remove(wrapper);
    }

    @Override
    public List<RoleMenu> selectByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new SinroException(ExceptionType.ROLE_ID_EMPTY);
        }
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq(RoleMenu.ROLE_ID, roleId);
        return this.list(wrapper);
    }
}

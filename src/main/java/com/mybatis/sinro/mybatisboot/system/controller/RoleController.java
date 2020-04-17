package com.mybatis.sinro.mybatisboot.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.common.annotation.PathRestController;
import com.mybatis.sinro.mybatisboot.controller.BaseController;
import com.mybatis.sinro.mybatisboot.system.bean.RoleAo;
import com.mybatis.sinro.mybatisboot.system.bean.RoleDetailVo;
import com.mybatis.sinro.mybatisboot.system.bean.RoleListVo;
import com.mybatis.sinro.mybatisboot.system.entity.Role;
import com.mybatis.sinro.mybatisboot.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */

@PathRestController("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/list/page")
    public String listPage(@RequestBody RoleAo roleAo){
        IPage page = roleService.page(new Page<>());
        return success(page, RoleListVo.class);
    }

    @PostMapping("/list")
    public String list(@RequestBody RoleAo roleAo){
        List<Role> roleList = roleService.list(roleAo);
        return success(roleList, RoleListVo.class);
    }

    @PostMapping("/add")
    public String add(@RequestBody RoleAo roleAo){
        String roleId = roleService.addRole(roleAo);
        return success(roleId);
    }

    @PostMapping("/update")
    public String update(@RequestBody RoleAo roleAo){
        roleService.updateRole(roleAo);
        return SUCCESS_RESULT;
    }

    @GetMapping("/detail/{roleId}")
    public String detail(@PathVariable String roleId){
        Role role = roleService.getRoleByRoleId(roleId);
        return success(role, RoleDetailVo.class);
    }

    @GetMapping("/delete/{roleId}")
    public String delete(@PathVariable String roleId){
        roleService.deleteRole(roleId);
        return SUCCESS_RESULT;
    }

}


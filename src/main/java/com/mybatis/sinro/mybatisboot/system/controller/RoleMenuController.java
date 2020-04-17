package com.mybatis.sinro.mybatisboot.system.controller;


import com.mybatis.sinro.mybatisboot.common.annotation.PathRestController;
import com.mybatis.sinro.mybatisboot.controller.BaseController;
import com.mybatis.sinro.mybatisboot.system.bean.RoleMenusAo;
import com.mybatis.sinro.mybatisboot.system.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 菜单角色关联表 前端控制器
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
@PathRestController("/role/menu")
public class RoleMenuController extends BaseController {

    @Autowired
    private IRoleMenuService roleMenuService;

    @PostMapping("/bind")
    public String bind(@RequestBody RoleMenusAo roleMenusAo){
        roleMenuService.roleBindMenus(roleMenusAo);
        return SUCCESS_RESULT;
    }

}


package com.mybatis.sinro.mybatisboot.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.sinro.mybatisboot.system.bean.RoleAo;
import com.mybatis.sinro.mybatisboot.system.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
public interface IRoleService extends IService<Role> {

    /**
     * 角色分页列表
     * @param page
     * @param roleAo
     * @return List<Role>
     */
    List<Role> listByPage(Page<Role> page, RoleAo roleAo);

    /**
     * 角色列表
     * @param roleAo
     * @return List<Role>
     */
    List<Role> list(RoleAo roleAo);

    /**
     * 添加角色
     * @param roleAo
     * @return String
     */
    String addRole(RoleAo roleAo);

    /**
     * 修改角色
     * @param roleAo
     */
    void updateRole(RoleAo roleAo);

    /**
     * 检测角色参数信息
     * @param roleAo
     * @param isAdd
     */
    void checkRole(RoleAo roleAo, boolean isAdd);

    /**
     * 根据角色名称查询角色
     * @param roleName
     * @return Role
     */
    Role getRoleByRoleName(String roleName);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(String roleId);

    /**
     * 根据角色Id查询角色信息
     * @param roleId
     * @return Role
     */
    Role getRoleByRoleId(String roleId);

    /**
     * 根据角色Id查询角色信息
     * @param roleIds
     * @return List<Role>
     */
    List<Role> selectListByRoleIds(List<String> roleIds);

    /**
     * 检查是否为管理员角色
     * @param roleId
     */
    void checkAdmin(String roleId);
}

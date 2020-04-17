package com.mybatis.sinro.mybatisboot.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.system.entity.Role;
import com.mybatis.sinro.mybatisboot.system.bean.RoleAo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 角色分页列表
     * @param page
     * @param roleAo
     * @return List<Role>
     */
    List<Role> findListByPage(@Param("page") Page<Role> page, @Param("roleAo") RoleAo roleAo);

    /**
     * 角色列表
     * @param roleAo
     * @return List<Role>
     */
    List<Role> findList(@Param("roleAo") RoleAo roleAo);
}

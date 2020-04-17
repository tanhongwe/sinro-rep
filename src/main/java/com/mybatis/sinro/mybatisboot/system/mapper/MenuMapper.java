package com.mybatis.sinro.mybatisboot.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.system.bean.MenuNode;
import com.mybatis.sinro.mybatisboot.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 菜单分页查询
     * @param page
     * @param menuType
     * @return List<Menu>
     */
    List<Menu> selectListByPage(@Param("page") Page<Menu> page, @Param("menuType") Integer menuType);

    /**
     * 菜单查询
     * @param menuType
     * @return List<Menu>
     */
    List<Menu> selectMenuList(@Param("menuType") Integer menuType);

    /**
     * 查询菜单树形结构
     * @param pid
     * @param menuType
     * @param roleId
     * @return List<MenuNode>
     */
    List<MenuNode> selectListByPid(@Param("pid") Integer pid, @Param("menuType") Integer menuType, @Param("roleId") String roleId);

    /**
     * 根据菜单类型及路径获取菜单
     * @param menuType
     * @param url
     * @return Menu
     */
    Menu selectMenuByTypeAndUrl(@Param("menuType") Integer menuType, @Param("url") String url);

    /**
     * 查询有路径的菜单
     * @param menuType
     * @param roleId
     * @return List<Menu>
     */
    List<Menu> selectListHasUrl(@Param("menuType") Integer menuType, @Param("roleId") String roleId);

    /**
     * 查询有指定菜单ID的子菜单
     * @param url
     * @param menuType
     * @param roleId
     * @return List<Menu>
     */
    List<Menu> selectChildren(@Param("url") String url, @Param("menuType") Integer menuType, @Param("roleId") String roleId);
}

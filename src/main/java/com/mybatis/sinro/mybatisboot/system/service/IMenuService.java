package com.mybatis.sinro.mybatisboot.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.sinro.mybatisboot.system.bean.MenuNode;
import com.mybatis.sinro.mybatisboot.system.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 添加菜单
     * @param menu
     * @return Integer
     */
    Integer addMenu(Menu menu);

    /**
     * 修改菜单
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 判断菜单参数
     * @param menu
     * @param isAdd
     */
    void checkMenu(Menu menu, boolean isAdd);

    /**
     * 菜单分页查询
     * @param page
     * @param menuType
     * @return List<Menu>
     */
    List<Menu> listByPage(Page<Menu> page, Integer menuType);

    /**
     * 菜单查询
     * @param menuType
     * @return List<Menu>
     */
    List<Menu> list(Integer menuType);

    /**
     * 根据菜单ID查询菜单
     * @param menuIds
     * @return List<Menu>
     */
    List<Menu> selectByMenuIds(List<Integer> menuIds);

    /**
     * 根据菜单ID删除菜单
     * @param menuId
     */
    void deleteMenuById(Integer menuId);

    /**
     * 根据菜单ID查询菜单
     * @param menuId
     * @return Menu
     */
    Menu getMenuById(Integer menuId);

    /**
     * 查询菜单树形结构
     * @param menuType
     * @return List<MenuNode>
     */
    List<MenuNode> tree(Integer menuType);

    /**
     * 查询当前菜单子菜单
     * @param pid
     * @param menuType
     * @param roleId
     * @return List<Menu>
     */
    List<Menu> selectByPid(Integer pid, Integer menuType, String roleId);

    /**
     * 根据菜单类型及路径获取菜单
     * @param menuType
     * @param url
     * @return Menu
     */
    Menu getMenuByTypeAndUrl(Integer menuType, String url);

    /**
     * 查询有路径的菜单
     * @return List<Menu>
     */
    List<Menu> listHasUrl();

    /**
     * 查询有指定菜单URL的子菜单
     * @param url
     * @return List<Menu>
     */
    List<Menu> childrenButton(String url);

    /**
     * 获取菜单列表
     * @return List<MenuNode>
     */
    List<MenuNode> menuTree();
}

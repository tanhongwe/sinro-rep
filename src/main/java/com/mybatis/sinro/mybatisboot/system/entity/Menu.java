package com.mybatis.sinro.mybatisboot.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单编号
     */
    @TableField("pid")
    private Integer pid;
    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 菜单图标
     */
    @TableField("menu_icon")
    private String menuIcon;
    /**
     * url地址
     */
    @TableField("menu_url")
    private String menuUrl;
    /**
     * 菜单排序号
     */
    @TableField("menu_index")
    private Integer menuIndex;
    /**
     * 类型（1：菜单  2：按钮  3：接口）
     */
    @TableField("menu_type")
    private Integer menuType;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    public static final String ID = "id";

    public static final String PID = "pid";

    public static final String MENU_NAME = "menu_name";

    public static final String MENU_ICON = "menu_icon";

    public static final String MENU_URL = "menu_url";

    public static final String MENU_INDEX = "menu_index";

    public static final String MENU_TYPE = "menu_type";

    public static final String REMARK = "remark";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

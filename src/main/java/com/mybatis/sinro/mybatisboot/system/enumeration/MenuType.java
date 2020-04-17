package com.mybatis.sinro.mybatisboot.system.enumeration;

import java.util.Objects;

/**
 * @author wxjason
 */

public enum MenuType {

    /**
     * 菜单
     */
    MENU(1, "菜单"),

    /**
     * 按钮
     */
    BUTTON(2, "按钮"),

    /**
     * 接口
     */
    INTERFACE(3, "接口");

    private Integer type;
    private String desc;

    MenuType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static MenuType menuType(Integer type) {
        if (Objects.nonNull(type)) {
            for (MenuType t : MenuType.values()) {
                if (Objects.nonNull(t.type) && t.type.intValue() == type.intValue()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static String desc (Integer code) {
        MenuType menuType = menuType(code);
        return Objects.nonNull(menuType) ? menuType.getDesc() : "";
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

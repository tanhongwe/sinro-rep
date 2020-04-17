package com.mybatis.sinro.mybatisboot.system.enumeration;

import java.util.Objects;

public enum CacheType {

    /**
     * 菜单
     */
    CODE(1, "验证码"),

    /**
     * 按钮
     */
    TOKEN(2, "Token");


    private Integer type;
    private String desc;

    CacheType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CacheType cacheType(Integer type) {
        if (Objects.nonNull(type)) {
            for (CacheType t : CacheType.values()) {
                if (Objects.nonNull(t.type) && t.type.intValue() == type.intValue()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static String desc (Integer code) {
        CacheType menuType = cacheType(code);
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


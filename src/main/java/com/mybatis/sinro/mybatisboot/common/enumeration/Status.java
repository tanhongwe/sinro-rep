package com.mybatis.sinro.mybatisboot.common.enumeration;

import java.util.Objects;

/**
 * @author wxjason
 */

public enum Status {

    /**
     * 启用
     */
    ENABLE(1, "启用"),
    /**
     * 停用
     */
    DISABLE(0, "停用");
    private Integer status;
    private String desc;

    Status(Integer code, String desc) {
        this.status = code;
        this.desc = desc;
    }

    public static Status status(Integer status) {
        if (Objects.nonNull(status)) {
            for (Status t : Status.values()) {
                if (Objects.nonNull(t.status) && t.status.intValue() == status.intValue()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static String desc (Integer status) {
        Status status1 = status(status);
        return Objects.nonNull(status1) ? status1.getDesc() : "";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

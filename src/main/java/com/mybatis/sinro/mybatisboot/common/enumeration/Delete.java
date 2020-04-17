package com.mybatis.sinro.mybatisboot.common.enumeration;

import java.util.Objects;

/**
 * @author wxjason
 */

public enum Delete {

    /**
     * 正常
     */
    NORMOL(1, "正常"),
    /**
     * 删除
     */
    DELETE(0, "删除");
    private Integer code;
    private String desc;

    Delete(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Delete delete(Integer code) {
        if (Objects.nonNull(code)) {
            for (Delete t : Delete.values()) {
                if (Objects.nonNull(t.code) && t.code.intValue() == code.intValue()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static String desc (Integer code) {
        Delete cameraConnectionState = delete(code);
        return Objects.nonNull(cameraConnectionState) ? cameraConnectionState.getDesc() : "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

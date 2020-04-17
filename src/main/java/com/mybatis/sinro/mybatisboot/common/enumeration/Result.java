package com.mybatis.sinro.mybatisboot.common.enumeration;

import java.util.Objects;

/**
 * @author wxjason
 */

public enum Result {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 失败
     */
    FAILED(0, "失败"),
    /**
     * 未知
     */
    UNKNOWN(2, "-");
    private Integer code;
    private String desc;

    Result(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Result result(Integer code) {
        if (Objects.nonNull(code)) {
            for (Result t : Result.values()) {
                if (Objects.nonNull(t.code) && t.code.intValue() == code.intValue()) {
                    return t;
                }
            }
        }
        return UNKNOWN;
    }

    public static String desc (Integer code) {
        return result(code).getDesc();
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

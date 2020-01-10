package com.mybatis.sinro.mybatisboot.common.exception;


import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import lombok.Data;

/**
 * 自定义的异常
 *
 * @author tanhongwei
 * @Date 2017/12/28 下午10:32
 */
@Data
public class SinroException extends RuntimeException {

    private Integer code;

    private String message;

    private Object data;

    private ExceptionType exceptionEnum;

    public SinroException(Integer code, String message) {
        this(code, message, null);
    }

    public SinroException(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public SinroException(ExceptionType exceptionEnum) {
        this(exceptionEnum, null);
    }

    public SinroException(ExceptionType exceptionEnum, Object data) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
        this.exceptionEnum = exceptionEnum;
        this.data = data;
    }
}

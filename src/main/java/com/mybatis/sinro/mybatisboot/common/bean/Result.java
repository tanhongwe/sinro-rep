package com.mybatis.sinro.mybatisboot.common.bean;


import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.utils.JsonUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前台的错误提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int code;

    protected String message;

    protected T data;

    public Result() {
    }

    public Result(ExceptionType exceptionType) {
        super();
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }

    public Result(T data){
        this(ExceptionType.HANDLE_SUCCESS);
        this.data = data;
    }

    public Result(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @SuppressWarnings("unchecked")
    public static <T> String success(T data) {
        Result result = new Result(ExceptionType.HANDLE_SUCCESS);
        result.setData(data);
        return JsonUtils.toJson(result);
    }
    public static String success() {
        Result result = new Result(ExceptionType.HANDLE_SUCCESS);
        return JsonUtils.toJson(result);
    }
    public static String fail(ExceptionType exceptionType) {
        Result result = new Result(exceptionType);
        return JsonUtils.toJson(result);
    }
    public static String fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> String fail(Integer code, String message, T data) {
        Result result = new Result(code, message);
        result.setData(data);
        return JsonUtils.toJson(result);
    }

    public static String fail(Exception e) {
        return fail(NumberConstants.FIVE_HUNDRED,e.getMessage());
    }
}

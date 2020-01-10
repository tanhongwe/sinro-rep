package com.mybatis.sinro.mybatisboot.common.handler;

import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.rsa.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author WangJinbo
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    public String serverError(Exception e) {
        log.error("未知异常:", e);
        return Result.fail(e);
    }

    @ExceptionHandler(SinroException.class)
    public String sinroExceptionError(SinroException e) {
        log.error("抛出异常:", e);
        return Result.fail(e.getExceptionEnum());
    }

}

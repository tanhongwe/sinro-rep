package com.mybatis.sinro.mybatisboot.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/30 11:05
 * @Description: 是否是导出字段，可在类上或者字段上，默认加了此注释为可导出，字段多的情况可在类上加上
 *               此注释，然后在不需要的字段上加此注释并且设置为false
 */
@Target(value = {TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelExportColumn {

    boolean value() default true;
}

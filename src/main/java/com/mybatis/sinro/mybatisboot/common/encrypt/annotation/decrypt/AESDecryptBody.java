package com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt;

import java.lang.annotation.*;

/**
 * @author licoy.cn
 * @version 2018/9/7
 * @see DecryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AESDecryptBody {

    String otherKey() default "";

}

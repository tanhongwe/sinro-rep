package com.mybatis.sinro.mybatisboot.common.encrypt.annotation.encrypt;


import com.mybatis.sinro.mybatisboot.common.encrypt.enums.SHAEncryptType;

import java.lang.annotation.*;

/**
 * @author licoy.cn
 * @version 2018/9/4
 * @see EncryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SHAEncryptBody {

    SHAEncryptType value() default SHAEncryptType.SHA256;

}

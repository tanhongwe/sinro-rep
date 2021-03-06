package com.mybatis.sinro.mybatisboot.common.encrypt.annotation.encrypt;


import com.mybatis.sinro.mybatisboot.common.encrypt.enums.EncryptBodyMethod;
import com.mybatis.sinro.mybatisboot.common.encrypt.enums.SHAEncryptType;

import java.lang.annotation.*;

/**
 * <p>加密{@link org.springframework.web.bind.annotation.ResponseBody}响应数据，可用于整个控制类或者某个控制器上</p>
 * @author licoy.cn
 * @version 2018/9/4
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptBody {

    EncryptBodyMethod value() default EncryptBodyMethod.MD5;

    String otherKey() default "";

    SHAEncryptType shaType() default SHAEncryptType.SHA256;

}

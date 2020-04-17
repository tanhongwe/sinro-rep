package com.mybatis.sinro.mybatisboot.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @ClassName: PathRestController
 * @Package com.tce.yunshi.common.annotation
 * @Description:
 * @Author wuxinjian
 * @Date 2018/11/2 9:09
 * @Version V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public @interface PathRestController {

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};
}

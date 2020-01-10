package com.mybatis.sinro.mybatisboot.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 16:25
 * @Description:
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入界面");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新界面");
    }
}

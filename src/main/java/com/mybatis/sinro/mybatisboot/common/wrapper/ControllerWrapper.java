package com.mybatis.sinro.mybatisboot.common.wrapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VO 转换类的控制器
 *
 * @author wxjason
 * @ClassName ControllerWrapper
 * @Auther: tan.hongwei
 * @Date: 2018/8/16
 * @Description:
 **/
@Component
public class ControllerWrapper implements InitializingBean, ApplicationContextAware {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ControllerWrapper.class);

    private static final Map<String, BaseWrapper> WRAPPERS = new ConcurrentHashMap<>();

    private ApplicationContext ctx;

    @Override
    public void afterPropertiesSet() {
        final Map<String, BaseWrapper> handlerMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx, BaseWrapper.class, true, true);
        handlerMap.values().forEach(p -> WRAPPERS.put(generateKey(p.getModelClass(), p.getDataClass()), p));
        logger.info("加载 VO 转化器完成,共有{}个", WRAPPERS.size());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public <T, F> List<F> warp(List<T> model, Class<F> dataClass) {
        List<F> data = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(model)) {
            model.forEach(t -> {
                try {
                    data.add(warp(t, dataClass));
                } catch (IOException e) {
                    throw new SinroException(ExceptionType.SERVER_ERROR);
                }
            });
        }
        return data;
    }

    public <T,F> IPage<F> pageWarp(IPage<T> page, Class<F> dataClass) {
        List<T> model = page.getRecords();
        List<F> data = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(model)) {
            model.forEach(t -> {
                try {
                    data.add(warp(t, dataClass));
                } catch (IOException e) {
                    throw new SinroException(ExceptionType.SERVER_ERROR);
                }
            });
        }
        IPage<F> pageF = new Page<>();
        page.setRecords(null);
        BeanUtils.copyProperties(page,pageF);
        pageF.setRecords(data);
        return pageF;
    }

    public <T, F> F warp(T model, Class<F> dataClass) throws IOException {
        BaseWrapper baseWrapper = WRAPPERS.get(generateKey(model.getClass(), dataClass));
        if (baseWrapper == null) {
            throw new RuntimeException("没有找到 " + model.getClass().getName() + " 的 VO 转化器" + generateKey(model.getClass(), dataClass));
        }
        return (F) baseWrapper.warp(model);
    }

    private String generateKey(Class<?> modelClass, Class<?> dataClass) {
        return modelClass.getName() + "_" + dataClass.getName();
    }
}

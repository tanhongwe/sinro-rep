package com.mybatis.sinro.mybatisboot.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.sinro.mybatisboot.system.entity.Cache;

/**
 * <p>
 * 缓存表 服务类
 * </p>
 *
 * @author zhouke
 * @since 2019-11-16
 */
public interface ICacheService extends IService<Cache> {

    void add(Cache cache);

    void clearCode();

    void clearToken();

    String checkCode(String key);

    Boolean checkToken(String key);
}

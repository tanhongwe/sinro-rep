package com.mybatis.sinro.mybatisboot.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.system.entity.Cache;
import com.mybatis.sinro.mybatisboot.system.enumeration.CacheType;
import com.mybatis.sinro.mybatisboot.system.mapper.CacheMapper;
import com.mybatis.sinro.mybatisboot.system.service.ICacheService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 缓存表
 * </p>
 *
 * @author zhouke
 * @since 2019-12-10
 */
@Service
public class CacheServiceImpl extends ServiceImpl<CacheMapper, Cache> implements ICacheService {

    @Override
    public void add(Cache cache) {
        if(cache.getType() == NumberConstants.ONE){
            this.clearCode();
        }else {
            this.clearToken();
        }
        Long time = System.currentTimeMillis() / NumberConstants.ONE_THOUSAND;
        cache.setCreateTime(time.intValue());
        this.save(cache);
    }

    @Override
    public void clearCode() {
        Long nowTime = System.currentTimeMillis() / NumberConstants.ONE_THOUSAND;
        Long expireTime = nowTime - 60*10;
        QueryWrapper<Cache> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1);
        wrapper.le("create_time",expireTime.intValue());
        this.remove(wrapper);
    }

    @Override
    public void clearToken() {
        Long nowTime = System.currentTimeMillis() / NumberConstants.ONE_THOUSAND;
        Long expireTime = nowTime - 60*30;
        QueryWrapper<Cache> wrapper = new QueryWrapper<>();
        wrapper.eq("type",2);
        wrapper.le("create_time",expireTime.intValue());
        this.remove(wrapper);
    }

    @Override
    public String checkCode(String key) {
        this.clearCode();
        QueryWrapper<Cache> wrapper = new QueryWrapper<>();
        wrapper.eq("type", CacheType.CODE.getType());
        wrapper.eq("code_key",key);
        Cache cache = this.getOne(wrapper);
        return cache.getCodeValue();
    }

    @Override
    public Boolean checkToken(String key) {
        this.clearToken();
        QueryWrapper<Cache> wrapper = new QueryWrapper<>();
        wrapper.eq("type", CacheType.TOKEN.getType());
        wrapper.eq("token_key",key);
        Cache cache = this.getOne(wrapper);
        if(Objects.isNull(cache)){
            return false;
        }
        return cache.getTokenValue().equals(key);
    }
}

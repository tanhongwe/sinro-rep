package com.mybatis.sinro.mybatisboot.system.controller;


import com.mybatis.sinro.mybatisboot.common.annotation.PathRestController;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.Result;
import com.mybatis.sinro.mybatisboot.controller.BaseController;
import com.mybatis.sinro.mybatisboot.system.entity.Cache;
import com.mybatis.sinro.mybatisboot.system.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 缓存表远程
 * </p>
 *
 * @author zhouke
 * @since 2019-11-15
 */
@PathRestController("/cache")
public class CacheController extends BaseController {

    @Autowired
    private ICacheService cacheService;


    @GetMapping("/add")
    public String add (@RequestParam(value = "token")String token) {
        Cache cache = new Cache();
        cache.setTokenValue(token);
        cache.setTokenKey(token);
        cache.setType(NumberConstants.TWO);
        cacheService.add(cache);
        return success(Result.SUCCESS);
    }

    @GetMapping("/check")
    public Boolean check (@RequestParam(value = "token")String token) {
        return cacheService.checkToken(token);
    }

}


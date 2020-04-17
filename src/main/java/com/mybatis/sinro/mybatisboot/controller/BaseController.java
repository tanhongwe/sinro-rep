package com.mybatis.sinro.mybatisboot.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.common.bean.BaseVo;
import com.mybatis.sinro.mybatisboot.common.bean.Result;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.common.wrapper.ControllerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wxjason
 */
@Component
public class BaseController {
    @Autowired
    protected ControllerWrapper controllerWrapper;
    protected static final String SUCCESS_RESULT = Result.success();
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected static final Integer DEFAULT_PAGE_SIZE = 20;

    protected static final Integer DEFAULT_MAX_PAGE_SIZE = 100;

    protected static final Integer DEFAULT_PAGE_INDEX = 0;

    protected static final Integer DEFAULT_MAX_PAGE_INDEX = 10000;

    protected HttpServletRequest getHttpServletRequest() {
        return HttpUtils.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpUtils.getResponse();
    }

    protected HttpSession getSession() {
        return HttpUtils.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpUtils.getRequest().getSession(flag);
    }

    protected String getParameter(String name) {
        return HttpUtils.getRequest().getParameter(name);
    }

    protected String getBasePath(){
        HttpServletRequest request = getHttpServletRequest();
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    }

    protected void setAttribute(String name, Object value) {
        HttpUtils.getRequest().setAttribute(name, value);
    }

    protected <T> String success(List<T> data) {
        return Result.success(data);
    }

    @SuppressWarnings("unchecked")
    protected <T, F> String success(Page page, List<T> model, Class<F> dataClass) {
        List<F> voList = warp(model, dataClass);
        page.setRecords(voList);
        page.setTotal(voList.size());
        return Result.success(page);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Model, F extends BaseVo> String success(Page page, List<T> model, Class<F> dataClass, Integer size, Integer current) {
        List<F> voList = warp(model, dataClass);
        page.setRecords(voList);
        return Result.success(new Page(current,size));
    }

    @SuppressWarnings("unchecked")
    protected <T> String success(Page page, List<T> data) {
        page.setRecords(data);
        return Result.success(page);
    }

    protected <T,F> String success(IPage<T> page, Class<F> dataClass) {
        return Result.success(controllerWrapper.pageWarp(page,dataClass));
    }

    protected <T, F> String success(List<T> model, Class<F> dataClass) {
        List<F> voList = warp(model, dataClass);
        return Result.success(voList);
    }

    protected <T> String success(T data) {
        return Result.success(data);
    }

    protected <T, F> String success(T model, Class<F> dataClass) {
        F vo = warp(model, dataClass);
        return Result.success(vo);
    }

    protected <T, F> List<F> warp(List<T> model, Class<F> dataClass) {
        if (CollectionUtils.isEmpty(model)) {
            return new ArrayList<>();
        }
        return controllerWrapper.warp(model, dataClass);
    }

    protected <T, F> F warp(T model, Class<F> dataClass) {
        try {
            if (Objects.isNull(model)) {
                return dataClass.newInstance();
            }
            return controllerWrapper.warp(model, dataClass);
        } catch (Exception e) {
            throw new SinroException(ExceptionType.SERVER_ERROR);
        }
    }

    protected Integer pageSize(Integer pageSize){
        if(Objects.isNull(pageSize)){
            return DEFAULT_PAGE_SIZE;
        }
        if(pageSize > DEFAULT_MAX_PAGE_SIZE || pageSize < 1){
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    protected Integer pageIndex(Integer pageIndex){
        if(Objects.isNull(pageIndex)){
            return DEFAULT_PAGE_INDEX;
        }
        if(pageIndex < DEFAULT_PAGE_INDEX || pageIndex > DEFAULT_MAX_PAGE_INDEX){
            return DEFAULT_PAGE_INDEX;
        }
        return pageIndex;
    }
}

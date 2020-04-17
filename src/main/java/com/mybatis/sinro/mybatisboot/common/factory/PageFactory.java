package com.mybatis.sinro.mybatisboot.common.factory;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.common.utils.RegexUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {

    private static final int DEFAULT_SIZE = 10;

    private static final int DEFAULT_CURRENT = 1;

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpUtils.getRequest();
        String sizeS = request.getParameter("size");
        String currentS = request.getParameter("current");
        //每页多少条数据
        int size = DEFAULT_SIZE;
        //每页的偏移量(本页当前有多少条)
        int current = DEFAULT_CURRENT;
        if (RegexUtils.matchNumber(sizeS) && Integer.parseInt(sizeS) != NumberConstants.ZERO) {
            size = Integer.parseInt(sizeS);
        }
        if (RegexUtils.matchNumber(currentS)) {
            current = Integer.parseInt(currentS);
        }
        Page<T> page = new Page<>(current, size);
        page.setSearchCount(true);
        return page;
    }
}

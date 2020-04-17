package com.mybatis.sinro.mybatisboot.common.excel.handler;


import com.mybatis.sinro.mybatisboot.common.bean.Count;

import java.util.List;

/**
 * @author tan.hongwei
 */
public interface ExcelHandle {
    /**
     * 处理Excel中每行数据
     * @param count
     * @param i
     * @param row
     */
    void handle(Count count, Integer i, List<Object> row);
}

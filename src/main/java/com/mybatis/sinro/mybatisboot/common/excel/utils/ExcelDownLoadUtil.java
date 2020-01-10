package com.mybatis.sinro.mybatisboot.common.excel.utils;

import com.mybatis.sinro.mybatisboot.common.annotation.ExcelExportColumn;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import com.mybatis.sinro.mybatisboot.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/30 10:05
 * @Description: EXCEL导出类
 */
@Slf4j
public class ExcelDownLoadUtil {

    private static ConcurrentHashMap<String,List<String>> map = new ConcurrentHashMap();

    /**
     * 一般导出 List<Object> 为行数据，把整个数据查出来 循环放入 List<List<Object>> excel 即可
     * @param excel
     * @param columnNames
     * @param response
     */
    public static void down(List<List<Object>> excel,String[] columnNames, HttpServletResponse response){
        Long startTime = System.currentTimeMillis();
        ExcelCreateFileUtils.createFile(excel, columnNames, response);
        Double useTime = (System.currentTimeMillis() - startTime + 0.0D) / NumberConstants.ONE_THOUSAND;
        log.info("消耗时间:{}秒",useTime);
    }

    /**
     * 简单少量导出，用注释@ExcelExportColumn可以控制导出字段，因为用到了反射，大量导出必然存在性能问题
     * @param list
     * @param columnNames
     * @param response
     */
    public static void simpleDown(List list, String[] columnNames, HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();
        List<List<Object>> excel = rowsDateInjection(list);
        ExcelCreateFileUtils.createFile(excel, columnNames, response);
        Double useTime = (System.currentTimeMillis() - startTime + 0.0D) / NumberConstants.ONE_THOUSAND;
        log.info("创建数据条数：{}条,消耗时间:{}秒",list.size(),useTime);
    }

    private static List<List<Object>> rowsDateInjection(List list) {
        List<List<Object>> excel = new ArrayList<>();
        if (Objects.isNull(CollectionUtils.getFirst(list))) {
            return excel;
        } else {
            List<String> nameList = getExportFieldNameList(CollectionUtils.getFirst(list).getClass());
            log.info("需要打印的字段名集合:{}",nameList.toString());
            if (CollectionUtils.isNotEmpty(nameList)) {
                list.forEach(data -> {
                    List<Object> row = new ArrayList<>();
                    for (String name : nameList) {
                        Field field;
                        Object rowData;
                        try {
                            field = data.getClass().getDeclaredField(name);
                            field.setAccessible(true);
                            rowData = field.get(data);
                        } catch (Exception e) {
                            throw new SinroException(ExceptionType.EXCEL_CREATE_EXCEPTION);
                        }
                        row.add(rowData);
                    }
                    excel.add(row);
                });
            }
        }
        return excel;
    }

    private static List<String> getExportFieldNameList(Class clazz){
        if (Objects.nonNull(map.get(clazz.getName()))) {
            return map.get(clazz.getName());
        }
        //需要导出字段名字数组
        List<String> fieldNameList = new LinkedList<>();
        ExcelExportColumn clazzAnno = (ExcelExportColumn) clazz.getAnnotation(ExcelExportColumn.class);
        if (Objects.isNull(clazzAnno)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ExcelExportColumn annotation = field.getAnnotation(ExcelExportColumn.class);
                if (Objects.nonNull(annotation)) {
                    if (annotation.value()) {
                        fieldNameList.add(field.getName());
                    }
                }
            }
        } else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ExcelExportColumn annotation = field.getAnnotation(ExcelExportColumn.class);
                if (Objects.nonNull(annotation) && Objects.nonNull(annotation.value())) {
                    if (!annotation.value()) {
                        continue;
                    }
                }
                fieldNameList.add(field.getName());
            }
        }
        map.put(clazz.getName(),fieldNameList);
        return fieldNameList;
    }
}

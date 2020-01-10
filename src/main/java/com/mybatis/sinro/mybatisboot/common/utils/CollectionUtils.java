package com.mybatis.sinro.mybatisboot.common.utils;

import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CollectionUtil
 * @Author: WangJinbo
 * @Date: 2018/10/26 14:32
 * @Description:
 **/
public class CollectionUtils extends org.springframework.util.CollectionUtils {
    /**
     * 数组是否为空
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为非空
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }
    /**
     * 集合是否为非空
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    /**
     * map是否为非空
     * @param map
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 获取第一个元素
     * @param tList
     * @return 第一个元素
     */
    public static <T>T getFirst(List<T> tList) {
        if(isEmpty(tList)){
            return null;
        }
        return tList.get(NumberConstants.ZERO);
    }
    /**
     * 获取第一个元素
     * @param array
     * @return 第一个元素
     */
    public static <T>T getFirst(T[] array) {
        if(isEmpty(array)){
            return null;
        }
        return array[NumberConstants.ZERO];
    }
}

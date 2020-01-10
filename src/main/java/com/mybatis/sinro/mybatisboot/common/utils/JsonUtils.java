package com.mybatis.sinro.mybatisboot.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

/**
 * @author tan.hongwei
 */
public final class JsonUtils {
    public final static Integer NON_INDEX = -1;

    private JsonUtils() {

    }

    private static ObjectMapper getInstance() {
        ObjectMapper instance = SingletonHolder.OBJECT_MAPPER;
        instance.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        instance.configure(FAIL_ON_EMPTY_BEANS, false);
        return instance;
    }

    private static final class SingletonHolder {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    }

    private static ObjectMapper instance() {
        ObjectMapper instance = SingletonInstanceHolder.OBJECT_MAPPER;
        instance.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        instance.configure(FAIL_ON_EMPTY_BEANS, false);
        instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return instance;
    }

    private static final class SingletonInstanceHolder {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    }



    /**
     * JSON数组 转换为 集合
     *
     * @param jsonArray
     * @return
     */
    public static <T> List<T> jsonArray2List(String jsonArray, Class<T> tClass) {
        JavaType javaType = instance().getTypeFactory().constructParametricType(ArrayList.class, tClass);
        try {
            return instance().readValue(jsonArray, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Object 转换为 JSON字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return instance().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    /**
     * Object 转换为 JSON字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return getInstance().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * JSON字符串 转换为 Object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T json2Obj(String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * JSON字符串 转换为 Object
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static final <T> T json2Obj(String json, TypeReference<?> type) {
        try {
            return (T) getInstance().readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    /**
     * JSON字符串 转换为 Object
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T map2Bean(Map<String,Object> map, Class<T> clazz) {
        try {
            return getInstance().readValue(obj2Json(map), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * JSON字符串 转换为 Object
     * 例如:
     * JavaType aa= SingletonHolder.OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, Student.class);
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, JavaType typeReference) {
        try {
            return getInstance().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 同上
     *
     * @param json
     * @param collectionClass  数组的类,例如ArrayList.class
     * @param parameterClasses 集合中元素的类例如Student.class
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> collectionClass, Class<?> parameterClasses) {
        JavaType collectionType = SingletonHolder.OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, parameterClasses);
        return json2Obj(json, collectionType);
    }

    public static Map<String, Object> json2Map(String json) {
        return json2Obj(json, new TypeReference<Map<String, Object>>(){});
    }
}

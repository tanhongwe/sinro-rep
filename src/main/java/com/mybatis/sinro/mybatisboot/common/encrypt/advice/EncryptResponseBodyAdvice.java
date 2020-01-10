package com.mybatis.sinro.mybatisboot.common.encrypt.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatis.sinro.mybatisboot.common.encrypt.EncryptBodyConfig;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.encrypt.*;
import com.mybatis.sinro.mybatisboot.common.encrypt.bean.EncryptAnnotationInfoBean;
import com.mybatis.sinro.mybatisboot.common.encrypt.enums.EncryptBodyMethod;
import com.mybatis.sinro.mybatisboot.common.encrypt.enums.SHAEncryptType;
import com.mybatis.sinro.mybatisboot.common.encrypt.util.*;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author: tan.hongwei
 * @Date: 2020/1/9 15:18
 * @Description:
 */
@Order(1)
@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {

    private final ObjectMapper objectMapper;

    private final EncryptBodyConfig config;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof EncryptBody ||
                        annotation instanceof AESEncryptBody ||
                        annotation instanceof DESEncryptBody ||
                        annotation instanceof RSAEncryptBody ||
                        annotation instanceof MD5EncryptBody ||
                        annotation instanceof SHAEncryptBody){
                    return true;
                }
            }
        }
        return methodParameter.getMethod().isAnnotationPresent(EncryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(AESEncryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(DESEncryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(RSAEncryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(MD5EncryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(SHAEncryptBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.isNull(o)) {
            return null;
        }
        serverHttpResponse.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String str = null;
        try {
            if (o instanceof String) {
                str = (String) o;
            } else {
                str = objectMapper.writeValueAsString(o);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        EncryptAnnotationInfoBean classAnnotation = getClassAnnotation(methodParameter.getDeclaringClass());
        if(classAnnotation!=null){
            return switchEncrypt(str, classAnnotation);
        }
        EncryptAnnotationInfoBean methodAnnotation = getMethodAnnotation(methodParameter);
        if(methodAnnotation!=null){
            return switchEncrypt(str, methodAnnotation);
        }
        throw new SinroException(ExceptionType.SERVER_ERROR);
    }

    /**
     * 获取类控制器上的加密注解信息
     * @param clazz 控制器类
     * @return 加密注解信息
     */
    private EncryptAnnotationInfoBean getClassAnnotation(Class clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof EncryptBody){
                    EncryptBody encryptBody = (EncryptBody) annotation;
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(encryptBody.value())
                            .key(encryptBody.otherKey())
                            .shaEncryptType(encryptBody.shaType())
                            .build();
                }
                if(annotation instanceof MD5EncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.MD5)
                            .build();
                }
                if(annotation instanceof SHAEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.SHA)
                            .shaEncryptType(((SHAEncryptBody) annotation).value())
                            .build();
                }
                if(annotation instanceof DESEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.DES)
                            .key(((DESEncryptBody) annotation).otherKey())
                            .build();
                }
                if(annotation instanceof AESEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.AES)
                            .key(((AESEncryptBody) annotation).otherKey())
                            .build();
                }
            }
        }
        return null;
    }

    /**
     * 获取方法控制器上的加密注解信息
     * @param methodParameter 控制器方法
     * @return 加密注解信息
     */
    private EncryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(EncryptBody.class)){
            EncryptBody encryptBody = methodParameter.getMethodAnnotation(EncryptBody.class);
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(encryptBody.value())
                    .key(encryptBody.otherKey())
                    .shaEncryptType(encryptBody.shaType())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(MD5EncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.MD5)
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(SHAEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.SHA)
                    .shaEncryptType(methodParameter.getMethodAnnotation(SHAEncryptBody.class).value())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.DES)
                    .key(methodParameter.getMethodAnnotation(DESEncryptBody.class).otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.AES)
                    .key(methodParameter.getMethodAnnotation(AESEncryptBody.class).otherKey())
                    .build();
        }
        return null;
    }

    /**
     * 选择加密方式并进行加密
     * @param formatStringBody 目标加密字符串
     * @param infoBean 加密信息
     * @return 加密结果
     */
    private String switchEncrypt(String formatStringBody,EncryptAnnotationInfoBean infoBean){
        EncryptBodyMethod method = infoBean.getEncryptBodyMethod();
        if(method==null){
            throw new SinroException(ExceptionType.ENCRYPT_METHOD_IS_NULL);
        }
        if(method == EncryptBodyMethod.MD5){
            return Md5EncryptUtil.encrypt(formatStringBody);
        }
        if(method == EncryptBodyMethod.SHA){
            SHAEncryptType shaEncryptType = infoBean.getShaEncryptType();
            if(shaEncryptType==null) {
                shaEncryptType = SHAEncryptType.SHA256;
            }
            return ShaEncryptUtil.encrypt(formatStringBody,shaEncryptType);
        }
        String key = infoBean.getKey();
        if(method == EncryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DesEncryptUtil.encrypt(formatStringBody,key);
        }
        if(method == EncryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AesEncryptUtil.encrypt(formatStringBody,key);
        }
        throw new SinroException(ExceptionType.SERVER_ERROR);
    }

}

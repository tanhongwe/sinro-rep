package com.mybatis.sinro.mybatisboot.common.encrypt.advice;


import com.mybatis.sinro.mybatisboot.common.encrypt.EncryptBodyConfig;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.AESDecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.DESDecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.DecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.annotation.decrypt.RSADecryptBody;
import com.mybatis.sinro.mybatisboot.common.encrypt.bean.DecryptAnnotationInfoBean;
import com.mybatis.sinro.mybatisboot.common.encrypt.bean.DecryptHttpInputMessage;
import com.mybatis.sinro.mybatisboot.common.encrypt.enums.DecryptBodyMethod;
import com.mybatis.sinro.mybatisboot.common.encrypt.util.AesEncryptUtil;
import com.mybatis.sinro.mybatisboot.common.encrypt.util.CheckUtils;
import com.mybatis.sinro.mybatisboot.common.encrypt.util.DesEncryptUtil;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.exception.SinroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 请求数据的加密信息解密处理<br>
 *     本类只对控制器参数中含有<strong>{@link org.springframework.web.bind.annotation.RequestBody}</strong>
 *     以及package为<strong><code>cn.licoy.encryptbody.annotation.decrypt</code></strong>下的注解有效
 * @see RequestBodyAdvice
 * @author licoy.cn
 * @version 2018/9/7
 */
@Order(1)
@ControllerAdvice
@Slf4j
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    @Autowired
    private EncryptBodyConfig config;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody ||
                        annotation instanceof AESDecryptBody ||
                        annotation instanceof DESDecryptBody ||
                        annotation instanceof RSADecryptBody){
                    return true;
                }
            }
        }
        return methodParameter.getMethod().isAnnotationPresent(DecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(RSADecryptBody.class);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if(inputMessage.getBody()==null){
            return inputMessage;
        }
        String body;
        try {
            body = IOUtils.toString(inputMessage.getBody(),config.getEncoding());
        }catch (Exception e){
            throw new SinroException(ExceptionType.BAD_REQUEST);
        }
        if(body==null || StringUtils.isEmpty(body)){
            throw new SinroException(ExceptionType.BAD_REQUEST);
        }
        String decryptBody = null;
        DecryptAnnotationInfoBean methodAnnotation = this.getMethodAnnotation(parameter);
        if(methodAnnotation!=null){
            decryptBody = switchDecrypt(body,methodAnnotation);
        }else{
            DecryptAnnotationInfoBean classAnnotation = this.getClassAnnotation(parameter.getDeclaringClass());
            if(classAnnotation!=null){
                decryptBody = switchDecrypt(body,classAnnotation);
            }
        }
        if(decryptBody==null){
            throw new SinroException(ExceptionType.BAD_REQUEST);
        }
        try {
            InputStream inputStream = IOUtils.toInputStream(decryptBody, config.getEncoding());
            return new DecryptHttpInputMessage(inputStream,inputMessage.getHeaders());
        }catch (Exception e){
            throw new SinroException(ExceptionType.BAD_REQUEST);
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 获取方法控制器上的加密注解信息
     * @param methodParameter 控制器方法
     * @return 加密注解信息
     */
    private DecryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(DecryptBody.class)){
            DecryptBody decryptBody = methodParameter.getMethodAnnotation(DecryptBody.class);
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(decryptBody.value())
                    .key(decryptBody.otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class)){
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(DecryptBodyMethod.DES)
                    .key(methodParameter.getMethodAnnotation(DESDecryptBody.class).otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class)){
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(DecryptBodyMethod.AES)
                    .key(methodParameter.getMethodAnnotation(AESDecryptBody.class).otherKey())
                    .build();
        }
        return null;
    }

    /**
     * 获取类控制器上的加密注解信息
     * @param clazz 控制器类
     * @return 加密注解信息
     */
    private DecryptAnnotationInfoBean getClassAnnotation(Class clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody){
                    DecryptBody decryptBody = (DecryptBody) annotation;
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(decryptBody.value())
                            .key(decryptBody.otherKey())
                            .build();
                }
                if(annotation instanceof DESDecryptBody){
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(DecryptBodyMethod.DES)
                            .key(((DESDecryptBody) annotation).otherKey())
                            .build();
                }
                if(annotation instanceof AESDecryptBody){
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(DecryptBodyMethod.AES)
                            .key(((AESDecryptBody) annotation).otherKey())
                            .build();
                }
            }
        }
        return null;
    }


    /**
     * 选择加密方式并进行解密
     * @param formatStringBody 目标解密字符串
     * @param infoBean 加密信息
     * @return 解密结果
     */
    private String switchDecrypt(String formatStringBody,DecryptAnnotationInfoBean infoBean){
        DecryptBodyMethod method = infoBean.getDecryptBodyMethod();
        if(method==null) {
            throw new SinroException(ExceptionType.BAD_REQUEST);
        }
        String key = infoBean.getKey();
        if(method == DecryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DesEncryptUtil.decrypt(formatStringBody,key);
        }
        if(method == DecryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AesEncryptUtil.decrypt(formatStringBody,key);
        }
        throw new SinroException(ExceptionType.BAD_REQUEST);
    }
}

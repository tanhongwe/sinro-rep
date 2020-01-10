package com.mybatis.sinro.mybatisboot.common.encrypt.bean;


import com.mybatis.sinro.mybatisboot.common.encrypt.enums.EncryptBodyMethod;
import com.mybatis.sinro.mybatisboot.common.encrypt.enums.SHAEncryptType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>加密注解信息</p>
 * @author licoy.cn
 * @version 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptAnnotationInfoBean {

    private EncryptBodyMethod encryptBodyMethod;

    private String key;

    private SHAEncryptType shaEncryptType;

}

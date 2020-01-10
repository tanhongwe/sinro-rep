package com.mybatis.sinro.mybatisboot.common.encrypt.util;


import com.mybatis.sinro.mybatisboot.common.encrypt.exception.KeyNotConfiguredException;
import org.springframework.util.StringUtils;

/**
 * <p>辅助检测工具类</p>
 * @author licoy.cn
 * @version 2018/9/7
 */
public class CheckUtils {

    public static String checkAndGetKey(String k1,String k2,String keyName){
        if(StringUtils.isEmpty(k1) && StringUtils.isEmpty(k2)){
            throw new KeyNotConfiguredException(String.format("%s is not configured (未配置%s)", keyName,keyName));
        }
        if(k1==null) {
            return k2;
        }
        return k1;
    }

}

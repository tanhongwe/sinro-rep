package com.mybatis.sinro.mybatisboot.common.encrypt.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * <p>DES加密处理工具类</p>
 * @author licoy.cn
 * @version 2018/9/5
 */
public class DesEncryptUtil {

    /**
     * DES加密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String encrypt(String content, String password){
        return des(content,password,Cipher.ENCRYPT_MODE);
    }


    /**
     * DES解密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String decrypt(String content, String password){
        return des(content,password,Cipher.DECRYPT_MODE);
    }


    /**
     * DES加密/解密公共方法
     * @param content  字符串内容
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     */
    private static String des(String content, String password, int type) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(type, keyFactory.generateSecret(desKey), random);

            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes("utf-8");
                return Base64Utils.encodeToString(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = Base64Utils.decodeFromString(content);
                return new String(cipher.doFinal(byteContent), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}

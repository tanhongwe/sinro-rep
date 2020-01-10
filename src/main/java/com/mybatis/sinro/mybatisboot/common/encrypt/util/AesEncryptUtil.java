
package com.mybatis.sinro.mybatisboot.common.encrypt.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>AES加密处理工具类</p>
 * @author wxjason
 */
public class AesEncryptUtil {

    private static final String ALGORITHM = "AES";


    private static final String ALGORITHMPROVIDER = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String encrypt(String content, String password){
        return aes(content,password,Cipher.ENCRYPT_MODE);
    }


    /**
     * AES解密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String decrypt(String content, String password){
        return aes(content,password,Cipher.DECRYPT_MODE);
    }

    /**
     * AES加密/解密 公共方法
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     */
    private static String aes(String content, String password, int type) {
        try {
            SecretKey secretKey = new SecretKeySpec(password.getBytes("utf-8"), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHMPROVIDER);
            cipher.init(type, secretKey);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] cipherBytes = cipher.doFinal(content.getBytes("utf-8"));
                return byteToHexString(cipherBytes);
            } else {
                byte[] hexBytes = hexStringToBytes(content);
                byte[] plainBytes = cipher.doFinal(hexBytes);
                return new String(plainBytes, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte转换为16进制字符串
     * @param src
     * @return
     */
    public static String byteToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串装换为byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        String content = "{}";
        String key = "4z@bqu7m$8@iQtyX";
        System.out.println("加密前:" + content);
        String e = encrypt(content, key);
        System.out.println("加密后:" + e);
        String d = decrypt(e, key);
        System.out.println("解密后:" + d);
    }
}

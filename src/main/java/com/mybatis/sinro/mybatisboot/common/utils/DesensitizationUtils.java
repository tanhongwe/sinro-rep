package com.mybatis.sinro.mybatisboot.common.utils;


import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DesensitizationUtils
 * @author tanhongwei
 * @Date 2020/4/26 15:58
 * @Version V1.0
 */
public class DesensitizationUtils {

    private static final String ASTERISK = "*";

    private static final int START_POINT = 9;

    private static final int START_END_POINT = 15;

    /**
     * 将字符串数据脱敏处理
     * @param source 源字符串
     * @return 脱敏后的字符串
     */
    public static String desensitize (String source) {
        // 源字符串为空，直接返回
        if (StringUtils.isBlank(source)) {
            return source;
        }
        int length = source.length();
        // 源字符串长度为1时，返回*
        if (length == NumberConstants.ONE) {
            return ASTERISK;
        }
        // 取出源字符串中第一位与最后一位字符
        // 源字符串长度为2-START_POINT时
        if (length <= START_POINT) {
            String firstChar = source.substring(NumberConstants.ZERO, NumberConstants.ONE);
            return getResult(length, firstChar, "");
        }
        // 源字符串长度为START_POINT-START_END_POINT时
        if (length <= START_END_POINT) {
            String firstChar = source.substring(NumberConstants.ZERO, NumberConstants.THREE);
            String lastChar = source.substring(length - NumberConstants.THREE, length);
            return getResult(length, firstChar, lastChar);
        }
        // 源字符串长度大于START_END_POINT时
        String firstChar = source.substring(NumberConstants.ZERO, NumberConstants.THREE);
        String lastChar = source.substring(length - NumberConstants.THREE, length);
        return getResult(length, firstChar, lastChar);
    }

    /**
     *
     * @param source    源字符串
     * @param start     从第几位开始脱敏
     * @param end       保留最后几位
     * @return
     */
    public static String desensitize (String source,int start,int end) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        int length = source.length();
        if (start > length || end > length || (length - start) < end) {
            return source;
        }
        String firstChar = source.substring(NumberConstants.ZERO, start);
        String lastChar = source.substring(length - end);
        return getResult(length,firstChar,lastChar);
    }

    /**
     * 根据起始字符串与结束字符串及总长度，获得脱敏后字符串
     * @param length 字符串总长度
     * @param startStr 起始字符串
     * @param lastChar 结束字符串
     * @return 脱敏后字符串
     */
    private static String getResult(int length, String startStr, String lastChar) {
        StringBuilder result = new StringBuilder(startStr);
        for (int i = startStr.length(); i < length - lastChar.length(); i++) {
            result.append(ASTERISK);
        }
        result.append(lastChar);
        return result.toString();
    }
}

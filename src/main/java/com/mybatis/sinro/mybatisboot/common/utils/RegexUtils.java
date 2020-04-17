package com.mybatis.sinro.mybatisboot.common.utils;


import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类<br>
 *
 * @author xiaoleilu
 */
public class RegexUtils {

    /**
     * 车牌号正则
     */
	private static final String RE_VEHICLE_CODE = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$";
	/**
	 * IP v4
	 */
	public final static Pattern IPV4 = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
	/**
	 * 图片base64
	 */
	private static final String IMAGE_BASE64_REG = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
	/**
	 * 数字
	 */
	public static final Pattern NUMBER = Pattern.compile("[^0-9]");
	/**
     * 邮箱正则
     */
	private static final String RE_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

	/**
	 * 名称正则(汉字、英文、数字及下划线)
	 */
	private static final String RE_NAME = "^[\\w|\\u4e00-\\u9fa5]{1,30}$";
	/**
	 * 名称正则(汉字、英文、数字及下划线)
	 */
	private static final String RE_NAME_WITH_SPACE = "^[\\s\\w|\\u4e00-\\u9fa5]{1,30}$";

    /**
     * 编码\用户名等正则(英文、数字及下划线)
     */
	private static final String RE_CODE = "^[\\w]{1,30}$";

	/**
	 * 中文正则
	 */
	private static final String RE_CHINESE = "^[\\u4e00-\\u9fa5]+$";

	/**
	 * 中文正则
	 */
	private static final String RE_NUMBER = "^[\\d]+$";
	/**
	 * 是否为base64编码
	 * @author wuxinjian
	 * @date 2019-05-17 16:51
	 */
	public static boolean isBase64 (String imageBase64) {
		return imageBase64.matches(IMAGE_BASE64_REG);
	}
	/**
	 * 给定内容是否匹配正则
	 *
	 * @param regex 正则
	 * @param content 内容
	 * @return 正则为null或者""则不检查，返回true，内容为null返回false
	 */
	public static boolean match(String regex, String content) {
		if (Objects.isNull(content)) {
			// 提供null的字符串为不匹配
			return false;
		}
		if (StringUtils.isEmpty(regex)) {
			// 正则不存在则为全匹配
			return true;
		}
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return pattern.matcher(content).matches();
	}
	public static boolean matchNumber(String num){
		return match(RE_NUMBER, num);
	}
	public static int number(String str){
		Matcher matcher = NUMBER.matcher(str);
		String number = matcher.replaceAll("");
		return Integer.parseInt(number);
	}
	/**
	 * 匹配姓名
	 * 只允许汉字、字母与数字的组合
	 * @param name
	 * @return
	 */
	public static boolean matchName(String name){
		return match(RE_NAME, name);
	}
	/**
	 * 匹配姓名
	 * 只允许汉字、字母与数字,空格的组合
	 * @param name
	 * @return
	 */
	public static boolean matchNameWithSpace(String name){
		return match(RE_NAME_WITH_SPACE, name);
	}
    /**
     * 匹配编码\用户名等
     * 只允许字母与数字的组合
     * @param code
     * @return
     */
    public static boolean matchCode(String code){
        return match(RE_CODE, code);
    }
	/**
	 * 只允许汉字
	 * @param chinese
	 * @return
	 */
	public static boolean matchChinese(String chinese){
		return match(RE_CHINESE, chinese);
	}
	/**
	 * 判断身份证
	 * @param card
	 * @return
	 */
	public static boolean matchCard(String card){
		return IDCardUtils.isValid(card);
	}
	/**
	 * 判断手机号
	 * 不为空且长度小于20
	 * @param phone
	 * @return
	 */
	public static boolean matchPhone(String phone){
		if(matchNumber(phone) && StringUtils.isNotEmpty(phone) && phone.length() <= NumberConstants.TWENTY){
			return true;
		}
		return false;
	}
    /**
     * 判断邮箱
     * @param email
     * @return
     */
    public static boolean matchEmail(String email){
        return match(RE_EMAIL, email);
    }
	/**
	 * 判断车牌号(包括新能源汽车)
	 * @param vehicleCode
	 * @return
	 */
	public static boolean matchVehicle(String vehicleCode){
		return match(RE_VEHICLE_CODE, vehicleCode);
	}
    /**
     * 判断IPv4
     * @param ipv4
     * @return
     */
    public static boolean matchIpv4(String ipv4){
        return isMatch(IPV4, ipv4);
    }
    /**
     * 判断IPv4
     * @param port
     * @return
     */
    public static boolean matchPort(Integer port) {
        return !Objects.isNull(port) && port > 0 && port < 65535;
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, String content) {
        // 提供null的字符串为不匹配
        return content != null && pattern != null && pattern.matcher(content).matches();
    }

	public static void main(String[] args) {
		System.out.println(matchNameWithSpace("  "));
	}
}
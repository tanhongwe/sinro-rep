/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mybatis.sinro.mybatisboot.common.utils;

import java.util.regex.Pattern;

/**
 * Web防火墙工具类
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */
public class SafeUtils {

    private static final Pattern SCRIPT_PATTERN_1 = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_PATTERN_2 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_PATTERN_3 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_PATTERN_4 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_PATTERN_5 = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_PATTERN_6 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_PATTERN_7 = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_PATTERN_8 = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	/**
	 * @Description 过滤XSS脚本内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripXSS(String value) {
		String rlt = null;

		if (null != value) {
			/// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);

			// Avoid null characters
			rlt = value.replaceAll("", "");

			// Avoid anything between script tags
			rlt = SCRIPT_PATTERN_1.matcher(rlt).replaceAll("");

			/// Avoid anything in a src='...' type of expression
			/*SCRIPT_PATTERN = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = SCRIPT_PATTERN.matcher(rlt).replaceAll("");

			SCRIPT_PATTERN = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = SCRIPT_PATTERN.matcher(rlt).replaceAll("");*/

			// Remove any lonesome </script> tag
			rlt = SCRIPT_PATTERN_2.matcher(rlt).replaceAll("");

			// Remove any lonesome <script ...> tag
			rlt = SCRIPT_PATTERN_3.matcher(rlt).replaceAll("");

			// Avoid eval(...) expressions
			rlt = SCRIPT_PATTERN_4.matcher(rlt).replaceAll("");

			// Avoid expression(...) expressions
			rlt = SCRIPT_PATTERN_5.matcher(rlt).replaceAll("");

			// Avoid javascript:... expressions
			rlt = SCRIPT_PATTERN_6.matcher(rlt).replaceAll("");

			// Avoid vbscript:... expressions
			rlt = SCRIPT_PATTERN_7.matcher(rlt).replaceAll("");

			// Avoid onload= expressions
			rlt = SCRIPT_PATTERN_8.matcher(rlt).replaceAll("");
		}
		
		return rlt;
	}

	/**
	 * @Description 过滤SQL注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripSqlInjection(String value) {
        /// value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
		return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", "");
	}

	/**
	 * @Description 过滤SQL/XSS注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripSqlXSS(String value) {
		return stripXSS(stripSqlInjection(value));
	}

}

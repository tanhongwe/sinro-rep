/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mybatis.sinro.mybatisboot.common.utils;


import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.constant.StringConstants;
import com.mybatis.sinro.mybatisboot.common.wrapper.RequestWrapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author tan.hongwei
 */
public class HttpUtils {

    private static final String UNKNOWN = "unknown";

    public static String getIp() {
        return getIpAddr(getRequest());
    }

    public static int getPort(){
        return getRequest().getRemotePort();
    }
    /***
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        // 这里和代理模块中要保持一致
        String ip = request.getHeader("X-Real-IP");

        // 下面的属于固定格式
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>(16);
        HttpServletRequest request = getRequest();
        Enumeration enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new RequestWrapper(request);
    }

    /**
     * 获取当前网络ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(StringConstants.LOCAL_IPV4) || ipAddress.equals(StringConstants.LOCAL_IPV6)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet != null ? inet.getHostAddress() : null;
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > NumberConstants.FIFTEEN) {
            if (ipAddress.indexOf(StringConstants.COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(StringConstants.COMMA));
            }
        }
        return ipAddress;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            StringBuffer query = new StringBuffer();

            for (Map.Entry<String, String> kv : param.entrySet()) {
                query.append(URLEncoder.encode(kv.getKey(), "UTF-8")).append("=");
                query.append(URLEncoder.encode(kv.getValue(), "UTF-8")).append(StringConstants.AND);
            }
            if (query.lastIndexOf(StringConstants.AND) > 0) {
                query.deleteCharAt(query.length() - 1);
            }

            String urlNameString = url + "?" + query.toString();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (Map.Entry<String, List<String>> m : map.entrySet()) {
                System.out.println(m.getKey() + "--->" + m.getValue());
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            StringBuilder para = new StringBuilder();
            for (Map.Entry<String, String> m : param.entrySet()) {
                para.append(m.getKey()).append("=").append(m.getValue()).append(StringConstants.AND);
            }
            if (para.lastIndexOf(StringConstants.AND) > 0) {
                para = new StringBuilder(para.substring(0, para.length() - 1));
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String getRequestBody(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StringConstants.DEFAULT_CHARSET)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.close(inputStream);
            FileUtils.close(reader);
        }
        return sb.toString();
    }

    public static String getRequestPath() {
        HttpServletRequest request = getRequest();
        return request.getServletPath();
    }
    public static String getMethod() {
        HttpServletRequest request = getRequest();
        return request.getMethod();
    }
    public static String getQueryString() {
        HttpServletRequest request = getRequest();
        return request.getQueryString();
    }

    public static String getRequestBody() {
        HttpServletRequest request = getRequest();
        try {
            InputStream inputStream = cloneInputStream(request.getInputStream());
            return getRequestBody(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken() {
        String t = getRequest().getHeader(JwtTokenUtils.TOKEN_HEADER);

        return Objects.nonNull(t) ? t.replace(JwtTokenUtils.TOKEN_PREFIX, "") : "";
    }

    public static String getUserId(){
        return getRequest().getHeader("userId");
    }

    public static String getUserIdFromToken(){
        return JwtTokenUtils.getUserId(getToken());
    }

    /**
     * 复制输入流
     *
     * @param inputStream
     * @return
     */
    public static InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

}

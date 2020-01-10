package com.mybatis.sinro.mybatisboot.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @ClassName: JwtTokenUtils
 * @Package com.tce.system.util
 * @Description:
 * @Author wuxinjian
 * @Date 2018/12/12 17:18
 * @Version V1.0
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecret";
    private static final String ISS = "echisan";

    /**
     * 过期时间是1800秒，既是30分钟
     */
    private static final long EXPIRATION = 30L * 60 * 1000;

    /**
     * 选择了记住我之后的过期时间为30天
     */
    private static final long EXPIRATION_REMEMBER = 7L * 24 * 60 * 60 * 1000;

    /**
     * 创建token
     */
    public static String createToken(String userId, String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setId(userId)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    /**
     * 从token中获取用户名
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取用户ID
     */
    public static String getUserId(String token){
        return getTokenBody(token).getId();
    }

    /**
     * 是否已过期
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
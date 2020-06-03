package com.mybatis.sinro.mybatisboot.common.jwt.filter;

import com.mybatis.sinro.mybatisboot.common.bean.Result;
import com.mybatis.sinro.mybatisboot.common.constant.StringConstants;
import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.utils.HttpUtils;
import com.mybatis.sinro.mybatisboot.common.utils.JsonUtils;
import com.mybatis.sinro.mybatisboot.common.utils.JwtTokenUtils;
import com.mybatis.sinro.mybatisboot.system.bean.LoginUser;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: JwtAuthenticationFilter
 * @Package com.tce.system.jwt.filter
 * @Description:
 * @Author wuxinjian
 * @Date 2018/12/13 14:48
 * @Version V1.0
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("开启配置jwt");
        String json = HttpUtils.getRequestBody();
        LoginUser user = JsonUtils.json2Obj(json,LoginUser.class);
        //这里的username会传给UserDetailsService的loadUserByUsername方法，作为loadUserByUsername的参数。
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // 允许子类设置详细属性
        setDetails(request, authRequest);

        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 成功验证后调用的方法,如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("开启配置success");
        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        User user = (User) authResult.getPrincipal();
        String token = JwtTokenUtils.createToken(user.getId(), user.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(Result.success());
    }

    /**
     * 这是验证失败时候调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("开启配置fail");
        Result result = new Result(ExceptionType.LOGIN_FAILED.getCode(), ExceptionType.LOGIN_FAILED.getMessage() + StringConstants.COLON + failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JsonUtils.toJson(result));
    }
}

package com.mybatis.sinro.mybatisboot.common.jwt.config;

import com.mybatis.sinro.mybatisboot.common.enumeration.ExceptionType;
import com.mybatis.sinro.mybatisboot.common.jwt.filter.JwtAuthenticationFilter;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import com.mybatis.sinro.mybatisboot.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: tan.hongwei
 * @Date: 2020/4/15 15:38
 * @Description:
 */
@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录处理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("开启配置http");
        http.cors().and().csrf().disable()
                .authorizeRequests()
                //访问：/home 无需登录认证权限
                .antMatchers(
                        "/actuator/**",
                        "/operation/log/add","/check/createImageCode","/check/checkImageCode/**","/cache/**",
                        "/operation/log/download/**","/user/**","/test/**").permitAll()
                //所有请求
                .anyRequest()
                //不需要验证
                //.permitAll()
                //需要验证
                .authenticated()
                .and()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    /**
     * 忽略拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        log.info("开启配置web");
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        //web.ignoring().antMatchers("/getUserInfo");
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }



    /**
     * 将用户设置在内存中
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("开启配置auth");
        // 在内存中配置用户，配置多个用户调用`and()`方法
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.info("开启配置provider");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 加密密码
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {

            @Autowired
            private IUserService userService;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                log.info("开启配置loaduser");
                log.info("登录用户为：{}", username);
                User user = userService.getByUsername(username);

                if (Objects.isNull(user)) {
                    //抛出错误，用户不存在
                    //throw new AccountExpiredException("用户名" + username + "不存在");
                    //为了避免登录人员穷举，这里不给出明显用户名不存在提示
                    throw new AccountExpiredException(ExceptionType.LOGIN_USERNAME_PASSWORD_ERROR.getMessage());
                }
                //获取用户权限
                final List<GrantedAuthority> authorities = new ArrayList<>();
                return user;
            }
        };
    }
}

package com.mybatis.sinro.mybatisboot.common.jwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName: PasswordEncoder
 * @Package com.tce.system.jwt.config
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/2 14:41
 * @Version V1.0
 */
@Slf4j
public class PasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("开启配置match");
        boolean same = rawPassword.toString().equals(encodedPassword);
        if (!same) {
            throw new BadCredentialsException("密码错误");
        }
        return true;
    }
}

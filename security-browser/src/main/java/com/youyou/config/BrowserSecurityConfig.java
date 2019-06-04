package com.youyou.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * create by luoxiaoqing
 * browser端的配置类
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean// 密码加密配置
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 默认通过这个方法来对密码加密，也可以自定义加密对象
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/default/login")
                .and()
                .authorizeRequests()
                .antMatchers("/login.html")
                .permitAll()
                .anyRequest()
                .authenticated()// 都需要身份认证
                .and()
                .csrf().disable();
    }
}

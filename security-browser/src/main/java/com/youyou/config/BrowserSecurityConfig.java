package com.youyou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * create by luoxiaoqing
 * browser端的配置类
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
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

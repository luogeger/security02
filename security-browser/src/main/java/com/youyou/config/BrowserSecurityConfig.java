package com.youyou.config;

import com.youyou.component.MyAuthenticationFailureHandler;
import com.youyou.component.MyAuthenticationSuccessHandler;
import com.youyou.filter.ValidateCodeFilter;
import com.youyou.yml.SecurityCoreYml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    private SecurityCoreYml securityCoreYml;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.error(securityCoreYml.getBrowser().getLoginPage());

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);// 添加校验失败的处理器
        validateCodeFilter.setSecurityCoreYml(securityCoreYml);// ==> implement InitializingBean
        validateCodeFilter.afterPropertiesSet();// 初始化成员变量值

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)// 添加图片验证码过滤器
                .formLogin()
                //.loginPage(browserYml.getLoginPage())
                .loginPage("/authentication/browser")// 不再是跳转到login.html，而是引导到身份验证的自定义controller，在里面写逻辑判断
                .loginProcessingUrl("/default/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()// 下面的都是授权的配置
                .antMatchers(
                        "/code/image",// 图片验证码
                        "/authentication/browser",// 身份验证的自定义controller
                        securityCoreYml.getBrowser().getLoginPage()// == demo-login.html
                        ).permitAll()// 这里的路径不需要身份热证
                .anyRequest()// 所有请求
                .authenticated()// 都需要身份认证
                .and().csrf().disable();// 关闭防跨站攻击 -- Could not verify the provided CSRF token because your session was not found
    }
}

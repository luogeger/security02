package com.youyou.config;

import com.youyou.validateCode.ValidateCodeGenerator;
import com.youyou.validateCode.imageCode.ImageCodeGenerator;
import com.youyou.yml.SecurityCoreYml;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by luoxiaoqing
 * 把图形验证码生成的方法可配置
 */
@Data
@Configuration
public class ValidateCodeGeneratorConfig {

    @Autowired
    private SecurityCoreYml securityCoreYml;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        return new ImageCodeGenerator().setSecurityCoreYml(securityCoreYml);
    }
}

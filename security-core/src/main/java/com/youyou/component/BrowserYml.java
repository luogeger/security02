package com.youyou.component;

import com.youyou.support.LoginType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * create by luoxiaoqing
 */
@Data
@Component
@ConfigurationProperties(prefix = "youyou.security")
public class BrowserYml {
    private String  loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;
}

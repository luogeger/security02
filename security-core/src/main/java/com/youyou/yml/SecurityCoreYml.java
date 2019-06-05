package com.youyou.yml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * create by luoxiaoqing
 * 所有和配置文件相关的属性都是从这个入口读取
 */
@Data
@Component
@ConfigurationProperties(prefix = "youyou.security")
public class SecurityCoreYml {
    private BrowserYml browser = new BrowserYml();


}

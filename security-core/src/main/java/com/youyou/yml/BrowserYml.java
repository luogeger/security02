package com.youyou.yml;

import com.youyou.support.LoginType;
import lombok.Data;

/**
 * create by luoxiaoqing
 */
@Data
public class BrowserYml {
    private String  loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;
}

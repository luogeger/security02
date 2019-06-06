package com.youyou.yml;

import lombok.Data;

/**
 * create by luoxiaoqing
 * 验证码
 */
@Data
public class ValidateCodeYml {
    private Integer expiredTime = 300;

    private Integer length = 4;
}

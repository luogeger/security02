package com.youyou.yml;

import lombok.Data;

/**
 * create by luoxiaoqing
 * 图形验证码
 */
@Data
public class ImageCodeYml extends ValidateCodeYml {
    private Integer width = 67;

    private Integer height = 23;

    private String  url;


}

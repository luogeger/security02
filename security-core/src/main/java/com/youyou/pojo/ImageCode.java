package com.youyou.pojo;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * create by luoxiaoqing
 * 图形验证码生成器
 */
@Data
public class ImageCode {
    private BufferedImage image;

    private String  code;

    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 设置过期时间并不会设置时间值，只会设置有效时间
     * @param image
     * @param code
     * @param second 有效时间
     */
    public ImageCode(BufferedImage image, String code, int second) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(second);
    }

}

package com.youyou.validateCode;

import com.youyou.pojo.ImageCode;

import javax.servlet.http.HttpServletRequest;

/**
 * create by luoxiaoqing
 */
public interface ValidateCodeGenerator {

    /**
     * 生成图形验证码
     * @param req
     * @return
     */
    ImageCode generate(HttpServletRequest req);
}

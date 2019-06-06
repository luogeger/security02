package com.youyou.controller;

import com.youyou.pojo.ImageCode;
import com.youyou.validateCode.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by luoxiaoqing
 * 获取验证码
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String SESSION_KEY = "SESSION_KEY_CODE_IMAGE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();// session工具类


    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    /**
     * 获取图片验证码
     * @param req
     * @param rep
     */
    @GetMapping("/image")
    public void getImageCode(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        /** 生成图形验证码*/
        ImageCode imageCode = imageCodeGenerator.generate(req);

        /** 存放到session，sessionStrategy会从当前的请求里面拿Session*/
        sessionStrategy.setAttribute(new ServletWebRequest(req), SESSION_KEY, imageCode);

        /** 响应出去*/
        ImageIO.write(imageCode.getImage(), "JPEG", rep.getOutputStream());
    }

}

package com.youyou.controller;

import com.youyou.pojo.ImageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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


    /**
     * 获取图片验证码
     * @param req
     * @param rep
     */
    @GetMapping("/image")
    public void getImageCode(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        /** 生成图形验证码*/
        ImageCode imageCode = createImageCode(req);

        /** 存放到session，sessionStrategy会从当前的请求里面拿Session*/
        sessionStrategy.setAttribute(new ServletWebRequest(req), SESSION_KEY, imageCode);

        /** 响应出去*/
        ImageIO.write(imageCode.getImage(), "JPEG", rep.getOutputStream());
    }


    /**
     * 生成图形验证码
     * @param req
     * @return
     */
    private ImageCode createImageCode(HttpServletRequest req) {
        int width = 67;
        int height = 23;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, 300);
    }

    /**
     * 生成随机背景条纹
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}

package com.youyou.validateCode.imageCode;

import com.youyou.pojo.ImageCode;
import com.youyou.validateCode.ValidateCodeGenerator;
import com.youyou.yml.SecurityCoreYml;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * create by luoxiaoqing
 * 图形验证码生成器
 */
@Data
@Accessors(chain = true)
public class ImageCodeGenerator implements ValidateCodeGenerator {
    private Logger logger = LoggerFactory.getLogger(getClass());


    private SecurityCoreYml securityCoreYml;


    /**
     * 生成图形验证码
     * @param req
     * @return
     */
    @Override
    public ImageCode generate(HttpServletRequest req) {

        int width = ServletRequestUtils.getIntParameter(req, "width", securityCoreYml.getImageCode().getWidth());
        int height = ServletRequestUtils.getIntParameter(req, "height", securityCoreYml.getImageCode().getHeight());
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
        for (int i = 0; i < securityCoreYml.getImageCode().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, securityCoreYml.getImageCode().getExpiredTime());

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

package com.youyou.controller;

import com.youyou.support.Response;
import com.youyou.yml.SecurityCoreYml;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by luoxiaoqing
 * 判断来自浏览器的请求是访问html/url, 然后跳转到login.html或返回401
 */
@RestController
public class BrowserSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();// security会把当前的请求信息缓存到session里面

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();// 工具类，做页面跳转使用

    @Autowired
    public SecurityCoreYml securityCoreYml;

    @RequestMapping("/authentication/browser")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)// 如果是访问的url，返回401状态码
    public Response browserAccessControl(HttpServletRequest req, HttpServletResponse res) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(req, res);// 获取当前请求的信息


        if (savedRequest != null) {
            String url = savedRequest.getRedirectUrl();

            logger.error("当前访问的url："+ url);
            if (StringUtils.endsWithIgnoreCase(url, ".html")) {
                redirectStrategy.sendRedirect(req, res, securityCoreYml.getBrowser().getLoginPage());// 重定向到login.html
            }
        }


        return new Response("当前访问需要权限！请引导到登陆页！");// 如果是url需要返回json，json对象的信息也需要做配置
    }
}

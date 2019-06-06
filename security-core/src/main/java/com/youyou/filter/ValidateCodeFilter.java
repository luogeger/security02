package com.youyou.filter;

import com.youyou.exception.ValidateCodeException;
import com.youyou.pojo.ImageCode;
import com.youyou.yml.SecurityCoreYml;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * create by luoxiaoqing
 * 验证码过滤器
 */
@Data
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();// session工具类

    private SecurityCoreYml securityCoreYml;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override // OncePerRequestFilter extends GenericFilterBean implements InitializingBean, 所以这里不会提示实现 afterPropertiesSet()
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityCoreYml.getImageCode().getUrl(), ",");
        String[] urls = securityCoreYml.getImageCode().getUrl().split(",");
        Stream.of(urls).forEach(url -> this.urls.add(url.trim()));
        this.urls.add("/default/login");// 登陆的请求一定要做图形验证码校验的

        this.urls.stream().forEach(logger::error);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /** 判断登陆请求：针对【/default/login】的【POST】的请求进行拦截过滤*/
        //boolean flag = StringUtils.equals("/default/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post");
        /** 判断登陆请求和yml文件的请求有没有能匹配上的，如果有才进行校验 */
        boolean[] flag = {false};// lambda改变外部变量需要定义为数组或实例变量
        this.urls.stream().forEach(url -> {if (pathMatcher.match(url, request.getRequestURI())) flag[0] = true;});

        /** 如果是就要验证，需要从session拿到信息，*/
        if (flag[0]) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                // 需要有针对验证码异常的处理  // 使用身份验证失败的处理器
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        /** 不是登陆请求的话就不做任何处理*/
        filterChain.doFilter(request, response);

    }


    /**
     * 验证码的校验逻辑
     * @param request
     */
    public void validate(ServletWebRequest request) throws ValidateCodeException, ServletRequestBindingException {

        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
                "SESSION_KEY_CODE_IMAGE");

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(request, "SESSION_KEY_CODE_IMAGE");
            throw new ValidateCodeException("验证码已过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, "SESSION_KEY_CODE_IMAGE");
    }
}

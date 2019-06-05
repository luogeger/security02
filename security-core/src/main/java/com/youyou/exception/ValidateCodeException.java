package com.youyou.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * create by luoxiaoqing
 * 验证码验证过程中出现的异常处理，AuthenticationException是Security针对身份验证的异常
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -5931058636511640470L;

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}

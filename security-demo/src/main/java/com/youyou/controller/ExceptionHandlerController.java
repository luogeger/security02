package com.youyou.controller;

import com.youyou.exception.UserNotException;
import com.youyou.pojo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * create by luoxiaoqing
 * 处理异常的控制器
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerController {


    @ExceptionHandler(UserNotException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response userNotException(UserNotException ex) {

        Response result = new Response();
        result.setMsg(ex.getMessage()).setData(ex.getId());

        return result;
    }
}

package com.youyou.support;

import lombok.Data;

/**
 * create by luoxiaoqing
 * 访问url需要返回的对象
 */
@Data
public class Response {
    private String msg;

    private Object data;

    public Response(String msg) {
        this.msg = msg;
    }
    public Response(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }


}
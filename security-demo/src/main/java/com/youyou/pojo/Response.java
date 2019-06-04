package com.youyou.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * create by luoxiaoqing
 * 统一的响应结果
 */
@Data
@Accessors(chain = true)
public class Response {
    private Integer status;
    private String  msg;
    private Object  data;

    public Response() {
    }

    public Response(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Response(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}

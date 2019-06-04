package com.youyou.exception;

import lombok.Data;

/**
 * create by luoxiaoqing
 */
@Data
public class UserNotException extends RuntimeException{
    private static final long serialVersionUID = -4274882764251938789L;

    private String  id;

    public UserNotException(String id) {
        super("user is exist...");
        this.id = id;
    }
}

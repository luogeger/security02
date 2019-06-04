package com.youyou.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * create by luoxiaoqing
 */
@Data
public class User {

    private String id;

    private String username;

    private String password;

    private Date birthday;
}

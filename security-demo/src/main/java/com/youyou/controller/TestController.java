package com.youyou.controller;

import com.youyou.exception.UserNotException;
import com.youyou.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * create by luoxiaoqing
 */
@RestController
@RequestMapping("")
public class TestController {


    private Logger logger = LoggerFactory.getLogger(getClass());



    @GetMapping("/queryAll")
    public List<User> queryAll() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @GetMapping("/queryById/{id:\\d+}")
    public User queryById(@PathVariable String id){
        throw new UserNotException(id);
    }

}

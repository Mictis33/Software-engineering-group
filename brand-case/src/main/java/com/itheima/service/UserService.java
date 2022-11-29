package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    /*用户登录*/
    public User login(User user);

    /*用户注册*/
    public boolean register(User user);

}

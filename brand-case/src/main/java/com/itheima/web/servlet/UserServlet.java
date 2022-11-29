package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.User;
import com.itheima.service.BrandService;
import com.itheima.service.UserService;
import com.itheima.service.impl.BrandServiceImpl;
import com.itheima.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService=new UserServiceImpl();

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader br=req.getReader();
        String params=br.readLine(); //json字符串


        User user=JSON.parseObject(params, User.class);

        /*调用service添加*/
        User loginUser=userService.login(user);

        if(loginUser!=null){
            /*响应成功的标识*/
            resp.getWriter().write("success");
        }
        else{
            resp.getWriter().write("fail");
        }


    }

    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader br=req.getReader();
        String params=br.readLine(); //json字符串


        User user=JSON.parseObject(params, User.class);

        /*调用service添加*/
        boolean noUser=userService.register(user);

        if(noUser==true){
            /*响应成功的标识*/
            resp.getWriter().write("success");
        }
        else{
            resp.getWriter().write("hasUser");
        }

    }

}

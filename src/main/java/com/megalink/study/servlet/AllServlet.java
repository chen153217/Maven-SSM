package com.megalink.study.servlet;

import com.megalink.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenhansen on 2017/7/21.
 */
@Component("AllServlet")
public class AllServlet extends HttpServlet{
    @Autowired
    private UserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if(url.indexOf("/login.do")!=-1){
            try {
                userService.login(req.getParameter("userName"), req.getParameter("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("/user-list".equals(url)){

        }
    }
}

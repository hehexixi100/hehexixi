package com.bjpowernode.crm.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 唐坤
 * 2021/6/22 0022
 */
@Controller
public class ToLoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/workbench/index.do")
    public String workBenchIndex(){
        return "workbench/index";
    }

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String ToLogin(HttpServletRequest request) {
        User user =null;
        String loginAct = null;
        String loginPwd = null;

        Cookie[] cookies = request.getCookies();

        if (cookies.length>0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("loginAct".equals(name)) {
                    loginAct = cookie.getValue();
                }
                if ("loginPwd".equals(name)) {
                    loginPwd = cookie.getValue();

                }
            }
        }

        if (loginAct != null && loginPwd != null) {

            Map <String ,Object> map = new HashMap<>();
            map.put("loginAct",loginAct);
            map.put("loginPwd", loginPwd);

            user = userService.selectByloginActAndloginPwd(map);
            request.getSession().setAttribute(Contants.SESSION_USER,user);

        }
        if(user != null){
            return "workbench/index";

        } else{

            return "settings/qx/user/login";
        }


    }

    @RequestMapping("/workbench/main/index.do")
    public String toindex(){
        return "workbench/main/index";
    }
}

package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 唐坤
 * 2021/6/25 0025
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object returnData(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //处理数据
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);
        Map map = new HashMap();

        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //传到数据库进行验证
        User user = userService.selectByloginActAndloginPwd(map);
        ReturnObject returnObject = new ReturnObject();
        //当前日期转换成字符串形式
        String now = DateUtils.formatDateTime(new Date());
        System.out.println("==================>"+now);
        //获取当前ip地址
        String remoteAddr = request.getRemoteAddr();
        System.out.println("=====================>"+remoteAddr);

        if(user == null){
//            returnObject.setMessage("登录成功！");
//            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

            returnObject.setMessage("您的账号或密码错误，请重新登录！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }else if(now.compareTo(user.getExpireTime())>0){
            returnObject.setMessage("你的账号已过期！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);

        }else if(!user.getAllowIps().contains(remoteAddr)){
            returnObject.setMessage("IP地址受限！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }else if("0".equals(user.getLockState())){
            returnObject.setMessage("账号已被锁定！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);

        }else {
            returnObject.setMessage("登录成功！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            session.setAttribute(Contants.SESSION_USER, user);
            if("true".equals(isRemPwd)){
                Cookie c1 = new Cookie("loginAct",loginAct);
                c1.setMaxAge(10*24*60*60);
                response.addCookie(c1);

                Cookie c2 = new Cookie("loginPwd",loginPwd);
                c2.setMaxAge(10*24*60*60);
                response.addCookie(c2);
            }else {
                Cookie c1 = new Cookie("loginAct",null);
                c1.setMaxAge(0);
                response.addCookie(c1);

                Cookie c2 = new Cookie("loginPwd",null);
                c2.setMaxAge(0);
                response.addCookie(c2);
            }

        }

        //传回去ReturnObject类
        return returnObject;
    }

    @RequestMapping("settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清楚cookie
        Cookie c1 = new Cookie("loginAct",null);
        c1.setMaxAge(0);
        response.addCookie(c1);

        Cookie c2 = new Cookie("loginPwd",null);
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session

        session.invalidate();


        return "redirect:/";
    }

}

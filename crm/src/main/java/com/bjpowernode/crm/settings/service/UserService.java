package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/6/22 0022
 */
public interface UserService {
    User selectByloginActAndloginPwd(Map<String,Object> map);

    List<User> selecAll();
}

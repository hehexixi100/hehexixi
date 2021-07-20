package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/6/22 0022
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectByloginActAndloginPwd(Map<String, Object> map) {
        return userMapper.selectByloginActAndloginPwd(map);
    }

    @Override
    public List<User> selecAll() {
        return userMapper.selecAll();
    }
}

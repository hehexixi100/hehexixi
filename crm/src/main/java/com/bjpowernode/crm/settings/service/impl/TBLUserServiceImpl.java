package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblUser;
import com.bjpowernode.crm.settings.domain.TblUserExample;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.TblUserMapper;
import com.bjpowernode.crm.settings.service.TBLUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 唐坤
 * 2021/6/22 0022
 */
@Service
public class TBLUserServiceImpl implements TBLUserService {

    @Autowired
    TblUserMapper tblUserMapper;

    @Override
    public List<TblUser> selectAll() {
        return tblUserMapper.selectByExample(new TblUserExample());
    }
}

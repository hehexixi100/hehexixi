package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblUser;
import com.bjpowernode.crm.settings.domain.User;

import java.util.List;

/**
 * 唐坤
 * 2021/6/22 0022
 */
public interface TBLUserService {
  List<TblUser> selectAll();
}

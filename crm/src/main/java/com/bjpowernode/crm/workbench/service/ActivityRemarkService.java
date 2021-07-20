package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * 唐坤
 * 2021/7/3 0003
 */
public interface ActivityRemarkService {
    //根据activity_id查询
    List<ActivityRemark> queryByactivityId(String activityId);
}

package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/6/29 0029
 */

public interface ActivityService {

    //保存创建的市场活动
    int saveActivity(Activity activity);

    //查询市场活动表，安多条件查询和分页
    List<Activity> queryActivityForPageByCondition(Map<String, Object> map);

    //根据条件去查询市场活动的数量
    long queryCountOfActivityByCondition(Map<String, Object> map);

    //根据id去查询市场活动（用于编辑）
    Activity queryActivityById(String id);

    //保存修改的市场活动
    int saveeditActivity(Activity activity);

    //根据ids来删除市场活动
    int deleteActivityByIds(String[] ids);

    //导出要抓取市场活动表的所有数据
    List<Activity> queryActivityForDetailByIds(String[] ids);

    //导入将excel中的多个市场活动导入到数据库市场活动表
    int saveActivityByList(List<Activity> activityList);

    //进入详情页面（用于详细）
    Activity queryActivityForDetailById(String id);

    //在其他模块中需要市场模块的支持
    List<Activity> queryAllActivityForDetail();

    //根据市场活动名查询所有市场活动
    List<Activity> queryActivityForDetailByName(String name);
    //根据id去查询市场活动（用于详细）
    Activity queryActivityDetailById(String id);

    //根据clue_id来查询相关的市场活动
    List<Activity> queryActivityByClueId(String clueId);

    //根据市场活动名称迷糊查询 和 clue_id 来查询相关的市场活动
    List<Activity> queryActivityByActivityNameAndClueId(Map<String,Object> map);
}

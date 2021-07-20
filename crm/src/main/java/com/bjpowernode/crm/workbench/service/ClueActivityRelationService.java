package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/7/4 0004
 */
public interface ClueActivityRelationService {


    //批量添加
    int saveClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);

    //根据clue_id和activity_id删除
    int deleteClueActivityRelationByClueIdAndActivityId(Map<String,Object> map);

}

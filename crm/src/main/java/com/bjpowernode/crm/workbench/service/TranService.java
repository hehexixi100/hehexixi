package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.StageAndCount;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/7/8 0008
 */
public interface TranService {
    int  saveTranAndMaybeSaveCustomer(Map<String, Object> map);

    //根据id查询一笔交易记录
    Tran queryTranById(String id);

    //查询交易阶段对应的交易笔数
    List<StageAndCount> queryStageAndCount();
}


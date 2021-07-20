package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * 唐坤
 * 2021/7/9 0009
 */
public interface TranHistoryService {

    //根据tranId查询交易历史记录
    List<TranHistory> queryTranHistoryByTranId(String tranId);
}

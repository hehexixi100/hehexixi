package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranRemark;

import java.util.List;

/**
 * 唐坤
 * 2021/7/9 0009
 */
public interface TranRemarkService {
    /**
     * 根据tranId查询该交易下所有备注的明细信息
     */
    List<TranRemark> queryTranRemarkForDetailByTranId(String tranId);
}

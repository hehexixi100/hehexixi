package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblDicValue;

import java.util.List;

/**
 * 唐坤
 * 2021/6/27 0027
 */
public interface TblDicValueService {

    //查询所有
    List<TblDicValue> queryTblDicValueAll();

    //通过id查询
    TblDicValue queryTblDicValueById(String id);

    //通过type_code查询
    List<TblDicValue> queryTblDicValueBytypeCode(String typeCode);

    //增加
    int saveTblDicValue(TblDicValue tblDicValue);

    //删除
    int deleteTblDicValue(String[] id);

    //更新
    int saveeditTblDicValue(TblDicValue tblDicValue);
}

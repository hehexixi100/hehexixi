package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.mapper.TblDicValueMapper;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 唐坤
 * 2021/6/27 0027
 */
@Service
public class TblDicValueServiceImpl implements TblDicValueService {

    @Autowired
    private TblDicValueMapper tblDicValueMapper;

    @Override
    public List<TblDicValue> queryTblDicValueAll() {
        return tblDicValueMapper.selectTblDicValueAll();
    }

    @Override
    public TblDicValue queryTblDicValueById(String id) {
        return tblDicValueMapper.selectTblDicValueById(id);
    }

    @Override
    public List<TblDicValue> queryTblDicValueBytypeCode(String typeCode) {
        return tblDicValueMapper.selectTblDicValueBytypeCode(typeCode);
    }


    @Override
    public int saveTblDicValue(TblDicValue tblDicValue) {
        return tblDicValueMapper.insertTblDicValue(tblDicValue);
    }

    @Override
    public int deleteTblDicValue(String[] id) {
        return tblDicValueMapper.deleteTblDicValue(id);
    }

    @Override
    public int saveeditTblDicValue(TblDicValue tblDicValue) {
        return tblDicValueMapper.updateTblDicValue(tblDicValue);
    }
}

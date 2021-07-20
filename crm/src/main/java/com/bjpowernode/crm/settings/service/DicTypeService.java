package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 唐坤
 * 2021/6/27 0027
 */

public interface DicTypeService {

    //查询所有的数据字典类型
    List<DicType> queryAllDicTypes();

    //根据code查询数据字典类型是否存在
    DicType queryDicTypeByCode(String code);

    //创建字典类型
    int saveDicType(DicType dicType);

    //删除
    int deleteDicTypeByCodes(String[] code);

    //更新
    int saveeditDicType(DicType dicType);
}

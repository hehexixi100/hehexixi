package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.TblDicValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDicValueMapper {
    int countByExample(TblDicValueExample example);

    int deleteByExample(TblDicValueExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblDicValue record);

    int insertSelective(TblDicValue record);

    List<TblDicValue> selectByExample(TblDicValueExample example);

    TblDicValue selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByExample(@Param("record") TblDicValue record, @Param("example") TblDicValueExample example);

    int updateByPrimaryKeySelective(TblDicValue record);

    int updateByPrimaryKey(TblDicValue record);

    //查询所有
    List<TblDicValue> selectTblDicValueAll();

    //通过id查询
    TblDicValue selectTblDicValueById(String id);

    //通过type_code查询
    List<TblDicValue> selectTblDicValueBytypeCode(String typeCode);


    //增加
    int insertTblDicValue(TblDicValue tblDicValue);

    //删除
    int deleteTblDicValue(String[] id);

    //更新
    int updateTblDicValue(TblDicValue tblDicValue);


}
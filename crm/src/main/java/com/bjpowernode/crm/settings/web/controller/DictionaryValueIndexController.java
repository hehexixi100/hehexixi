package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 唐坤
 * 2021/6/27 0027
 */
@Controller
public class DictionaryValueIndexController {

    @Autowired
    private TblDicValueService tblDicValueService;

    @Autowired
    private DicTypeService dicTypeService;

    //查询所有字典值数据，渲染在index页面上
    @RequestMapping("/settings/dictionary/value/index.do")
    public String dictionaryValueIndex(Model model) {
        List<TblDicValue> dicValueList = tblDicValueService.queryTblDicValueAll();
        if(dicValueList != null){
            model.addAttribute("dicValueList", dicValueList);

        }else {
            System.out.println("dicValueList 为 null。");
        }
        return "settings/dictionary/value/index";
    }

    @RequestMapping("/settings/dictionary/value/toSave.do")
    public String toSave(Model model){
        List<DicType> dicTypeList = dicTypeService.queryAllDicTypes();
        if(dicTypeList != null){
            model.addAttribute("dicTypeList", dicTypeList);

        }else {
            System.out.println("dicTypeList 为 null 。");
        }

        return "settings/dictionary/value/save";
    }

    @RequestMapping("/settings/dictionary/value/saveCreateDicValue.do")
     public @ResponseBody Object saveCreateDicValue(TblDicValue tblDicValue){
        ReturnObject returnObject = new ReturnObject();
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-", "");
        System.out.println(s);
        tblDicValue.setId(s);
        int result = tblDicValueService.saveTblDicValue(tblDicValue);
        if(result>0){
            //成功
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        }else {
            returnObject.setMessage("保存失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }

        return returnObject;

    }

    @RequestMapping("/settings/dictionary/value/editDicValue.do")
    public String editDicValue(String id, HttpServletRequest request){
        TblDicValue dicValue = tblDicValueService.queryTblDicValueById(id);
        if(dicValue != null){
            request.setAttribute("dicValue", dicValue);

        }else {
            System.out.println("dicValue 为 null。");
        }

        return "settings/dictionary/value/edit";
    }

    @RequestMapping("/settings/dictionary/value/saveEditDicValue.do")
    public @ResponseBody Object saveEditDicValuesaveEditDicValue(TblDicValue tblDicValue){
        ReturnObject returnObject = new ReturnObject();
        int result = tblDicValueService.saveeditTblDicValue(tblDicValue);
        System.out.println(tblDicValue);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        }else {
            returnObject.setMessage("更新失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/value/deleteDicValueByIds.do")
    public @ResponseBody Object deleteDicValueByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        int result = tblDicValueService.deleteTblDicValue(id);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        }else {
            returnObject.setMessage("删除失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }
        System.out.println("==================>" + result);

        return returnObject;
    }

}





















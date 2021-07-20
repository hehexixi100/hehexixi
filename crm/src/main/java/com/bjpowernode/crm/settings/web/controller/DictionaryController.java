package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;

import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 唐坤
 * 2021/6/27 0027
 */
@Controller
public class DictionaryController {
    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("/settings/dictionary/type/toSave.do")
    public String toSave() {

        return "settings/dictionary/type/save";
    }

    @RequestMapping("/settings/dictionary/type/save.do")
    public @ResponseBody
    Object saveaa(DicType dicType) {

        int result = dicTypeService.saveDicType(dicType);
        ReturnObject returnObject = new ReturnObject();
        if (result > 0) {
            returnObject.setMessage("创建成功");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        } else {
            returnObject.setMessage("创建失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);

        }


        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/judgeCode.do")
    public @ResponseBody Object judgeCode(String code) {
        // Object o = new Object();
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        ReturnObject returnObject = new ReturnObject();

        if (dicType == null) {
            //没有重复，可以添加
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            //有重复，不可以添加
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("编码重复，无法创建");
        }
        return returnObject;


    }

    @RequestMapping("/settings/dictionary/type/toEdit.do")
    public String toEdit(String code,HttpServletRequest request){
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        request.setAttribute("dicType", dicType);


        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/settings/dictionary/type/toSaveEdit.do")
    public @ResponseBody Object toSaveEdit(DicType dicType){
        ReturnObject returnObject = new ReturnObject();
        int result = dicTypeService.saveeditDicType(dicType);
        if(result>0){
            //修改成功
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            //失败
            returnObject.setMessage("更新失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }


        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/toDelete.do")
    public @ResponseBody Object toDelete(String[] code){
        ReturnObject returnObject = new ReturnObject();
      /*  String[] a = request.getParameterValues("code");
        for (String s : a) {
            System.out.println(s);
        }*/
        int result = dicTypeService.deleteDicTypeByCodes(code);
        System.out.println(result);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setMessage("删除失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }


        return returnObject;
    }

}


























package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 唐坤
 * 2021/6/27 0027
 */
@Controller
public class DictionaryIndexController {

    @Autowired
    private DicTypeService dicTypeService;


    @RequestMapping("/settings/dictionary/index.do")
    public String dictionaryIndex(){
        return "settings/dictionary/index";

    }

    //查询全部，数据渲染在index页面
    @RequestMapping("/settings/dictionary/type/index.do")
    public String typeIndex(Model model){
        List<DicType> dicTypeList = dicTypeService.queryAllDicTypes();
        model.addAttribute("dicTypeList", dicTypeList);


        return "settings/dictionary/type/index";
    }


}

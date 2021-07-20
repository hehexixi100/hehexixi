package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 唐坤
 * 2021/7/3 0003
 */
@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TblDicValueService tblDicValueService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String clueIndex(Model model){
        List<User> userList = userService.selecAll();
        model.addAttribute("userList",userList);
        List<TblDicValue> appellationList = tblDicValueService.queryTblDicValueBytypeCode("appellation");
        model.addAttribute("appellationList", appellationList);
        List<TblDicValue> clueStateList = tblDicValueService.queryTblDicValueBytypeCode("clueState");
        model.addAttribute("clueStateList", clueStateList);
        List<TblDicValue> sourceList = tblDicValueService.queryTblDicValueBytypeCode("source");
        model.addAttribute("sourceList", sourceList);


        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);

        ReturnObject returnObject = new ReturnObject();
        /*
        *           fullName       :fullName       ,
					appellation    :appellation    ,
					owner          :owner          ,
					company        :company        ,
					job            :job            ,
					email          :email          ,
					phone          :phone          ,
					website        :website        ,
					mphone         :mphone         ,
					state          :state          ,
					source         :source         ,
					description    :description    ,
					contactSummary :contactSummary ,
					nextContactTime:nextContactTime,
					address        :address
        * */
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        int result = clueService.saveClue(clue);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        }else {
            returnObject.setMessage("保存失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }

        return  returnObject;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id,Model model){
        Clue clue = clueService.queryClueById(id);
        model.addAttribute("clue", clue);
        List<Activity> activityList = activityService.queryActivityByClueId(id);
        model.addAttribute("activityList",activityList);


        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/searchActivity.do")
    public @ResponseBody Object searchActivity(String activityName,String clueId){
        Map<String,Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList = activityService.queryActivityByActivityNameAndClueId(map);

        return activityList;
    }
    //activityId=xxx&activityId=xxx&....&activityId=xxxx&clueId=xxxx
    @RequestMapping("/workbench/clue/saveBundActivity.do")
    public @ResponseBody Object saveBundActivity(String[] activityId, String clueId ){
        ReturnObject returnObject = new ReturnObject();
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        for (int i = 0; i <activityId.length; i++) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtils.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(activityId[i]);
            clueActivityRelationList.add(clueActivityRelation);
        }
        int result = clueActivityRelationService.saveClueActivityRelationByList(clueActivityRelationList);
        List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(activityList);

        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("关联失败！");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveUnbundActivity.do")
    public @ResponseBody Object saveUnbundActivity(String activityId,String clueId ){
        ReturnObject returnObject = new ReturnObject();
        Map<String ,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("activityId",activityId);
        int result = clueActivityRelationService.deleteClueActivityRelationByClueIdAndActivityId(map);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("解除关联失败！");
        }


        return returnObject;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    public String convertClue(String id,Model model){
        Clue clue = clueService.queryClueById(id);
        model.addAttribute("clue",clue);
        List<TblDicValue> stageList = tblDicValueService.queryTblDicValueBytypeCode("stage");
        model.addAttribute("stageList", stageList);


        return "workbench/clue/convert";

    }

    @RequestMapping("/workbench/clue/queryActivityForDetailByName.do")
    public @ResponseBody Object queryActivityForDetailByName(String activityName){
        List<Activity> activityList = activityService.queryActivityForDetailByName(activityName);
        return activityList;

    }

    @RequestMapping("/workbench/clue/saveConvertClue.do")
    public @ResponseBody Object saveConvertClue(String clueId,String isCreateTran,String amountOfMoney,String tradeName,String expectedClosingDate,String stage,String activityId,HttpSession session){
        Map<String ,Object> map = new HashMap<>();
        User user =(User) session.getAttribute(Contants.SESSION_USER);
        map.put("clueId",clueId);
        map.put("isCreateTran",isCreateTran);
        map.put("amountOfMoney",amountOfMoney);
        map.put("tradeName",tradeName);
        map.put("expectedClosingDate",expectedClosingDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("user",user);
        ReturnObject returnObject = new ReturnObject();
        try {
            clueService.toConvert(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("转换线索失败！");
        }


        return returnObject;
    }

  /*  @RequestMapping("/workbench/controller/hehe.do")
    public @ResponseBody Object hehe(String name){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = null;

        customer=new Customer();
        customer.setId("1");
        customer.setName("动力节点");
        customerList.add(customer);

          customer=new Customer();
        customer.setId("2");
        customer.setName("国庆节");
        customerList.add(customer);

          customer=new Customer();
        customer.setId("3");
        customer.setName("端午节");
        customerList.add(customer);



        return customerList;
    }*/
}































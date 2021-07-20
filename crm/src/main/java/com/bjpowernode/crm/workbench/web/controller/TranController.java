package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.TblDicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.TblDicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 唐坤
 * 2021/7/7 0007
 */
@Controller
public class TranController {
    @Autowired
    private UserService userService;

    @Autowired
    private TblDicValueService tblDicValueService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranHistoryService tranHistoryService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @RequestMapping("/workbench/transaction/index.do")
    public String tranIndex(){
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/createTran.do")
    public String createTran(Model model){
        List<User> userList = userService.selecAll();
        model.addAttribute("userList", userList);

        List<TblDicValue> stageList = tblDicValueService.queryTblDicValueBytypeCode("stage");
        model.addAttribute("stageList", stageList);

        List<TblDicValue> transactionTypeList = tblDicValueService.queryTblDicValueBytypeCode("transactionType");
        model.addAttribute("transactionTypeList", transactionTypeList);

        List<TblDicValue> sourceList = tblDicValueService.queryTblDicValueBytypeCode("source");
        model.addAttribute("sourceList", sourceList);


        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/queryCustomerByName.do")
    public @ResponseBody Object queryCustomerByName(String customerName){
        List<Customer> customerList = customerService.queryCustomerByName(customerName);
        System.out.println("**********************************");
        System.out.println(customerList);
        System.out.println(customerList.size());

        return customerList;

    }

    @RequestMapping("/workbench/transaction/getPossibilityByStageValue.do")
    public @ResponseBody Object getPossibilityByStageValue(String stageValue){
        ResourceBundle bundle = ResourceBundle.getBundle("AA");
        String name2score = bundle.getString(stageValue);
        return name2score;

    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(Tran tran , String customerName, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User) session.getAttribute(Contants.SESSION_USER);
        map.put("user",user );
        map.put("tran",tran );
        map.put("customerName", customerName);
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.saveTranAndMaybeSaveCustomer(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setMessage("保存失败！");
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
        }


        return returnObject;
    }

    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id,Model model){
        //根据id查询一条交易记录
        Tran tran = tranService.queryTranById(id);
        //读取属性文件，上传“可能性”
        ResourceBundle bundle = ResourceBundle.getBundle("AA");
        String possibility = bundle.getString(tran.getStage());
        tran.setPossibility(possibility);

        //根据tran_id查询交易历史
        List<TranHistory> tranHistoryList = tranHistoryService.queryTranHistoryByTranId(id);
        //交易备注
        List<TranRemark> tranRemarkList=tranRemarkService.queryTranRemarkForDetailByTranId(id);

        //上传stageList
        List<TblDicValue> stageList = tblDicValueService.queryTblDicValueBytypeCode("stage");

        model.addAttribute("tran", tran);
        String stage = tran.getStage();
        model.addAttribute("tranRemarkList",tranRemarkList);
        model.addAttribute("stageList", stageList);
        model.addAttribute("tranHistoryList",tranHistoryList);


        //最后成交阶段
        TranHistory tranHistory = tranHistoryList.get(tranHistoryList.size() - 1);
        System.out.println(tranHistory.getOrderNo());
        model.addAttribute("theOrderNo", tranHistory.getOrderNo());



        return "workbench/transaction/detail";
    }
}























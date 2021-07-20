package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.domain.StageAndCount;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 唐坤
 * 2021/7/9 0009
 */
@Controller
public class EchartTranController {
    @Autowired
    private TranService tranService;

    @RequestMapping("/workench/chart/transaction/index.do")
    public String indexTran(){
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workench/chart/transaction/queryCountOfTranGroupByStage.do")
    public @ResponseBody Object queryCountOfTranGroupByStage(){
        List<StageAndCount> stageAndCountList = tranService.queryStageAndCount();

        return stageAndCountList;
    }
}

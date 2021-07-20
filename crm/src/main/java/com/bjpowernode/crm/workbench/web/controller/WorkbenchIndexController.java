package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 唐坤
 * 2021/6/26 0026
 */
@Controller
public class WorkbenchIndexController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

//    @RequestMapping("/workbench/index.do")
//    public String enterWorkbenchIndex(){
//        return "workbench/index";
//    }

    //跳转到主页面
    @RequestMapping("/workbench/activity/index.do")
    public String toActivityIndex(Model model) {
        List<User> userList = userService.selecAll();
        model.addAttribute("userList",userList);


//
//           List<Activity> activityList = activityService.queryAllActivityForDetail();
//            if(activityList==null){
//                System.out.println("activityList is null ");
//
//            }else {
//                request.setAttribute("activityList",activityList);
//            }
//
//
//


        return "workbench/activity/index";
    }

    //分页展示
    @RequestMapping("/workbench/controller/toPaging.do")
    public @ResponseBody Object toPaging(int pageSize, int pageNo, String name, String owner, String startDate, String endDate) {
        Map<String ,Object>map = new HashMap<>();
        map.put("beginNo", (pageNo-1)*pageSize);
        map.put("pageSize", pageSize);
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        //按条件查询集合
        List<Activity> activityList = activityService.queryActivityForPageByCondition(map);
        //按条件查询总数量
        long totals = activityService.queryCountOfActivityByCondition(map);
        Map<String,Object> objMap=new HashMap<>();
        objMap.put("activityList",activityList);
        objMap.put("totals",totals);


        return objMap;


    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity ,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user =(User) session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        int result = activityService.saveActivity(activity);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败！");
        }



        return returnObject;

    }
    @RequestMapping("/workbench/activity/editActivity.do")
    public @ResponseBody Object editActivity(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;

    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity , HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user =(User) session.getAttribute(Contants.SESSION_USER);
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        activity.setEditBy(user.getId() );
        int result = activityService.saveeditActivity(activity);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("更新失败！");
        }

        return returnObject;

    }
    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        int result = activityService.deleteActivityByIds(id);
        if(result>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败！");
        }

        return returnObject;

    }

    //导出Excel文件
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletResponse response,String[] id) throws IOException {
        System.out.println("====================>" + id);
        List<Activity> activityList =null;
        if(id != null){
            activityList = activityService.queryActivityForDetailByIds(id);
        }else {

             activityList = activityService.queryAllActivityForDetail();
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动表");
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("id");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("owner");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("name");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("start_date");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("end_date");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("cost");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("description");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("create_time");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("create_by");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("edit_time");
        cell.setCellStyle(style);

        cell = row.createCell(10);
        cell.setCellValue("edit_by");
        cell.setCellStyle(style);

        if(activityList != null){
            for (int i = 0; i < activityList.size(); i++) {
                row=sheet.createRow(i+1);

                cell = row.createCell(0);
                cell.setCellValue(activityList.get(i).getId());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(activityList.get(i).getOwner());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(activityList.get(i).getName());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(activityList.get(i).getStartDate());
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(activityList.get(i).getEndDate());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(activityList.get(i).getCost());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(activityList.get(i).getDescription());
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(activityList.get(i).getCreateTime());
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(activityList.get(i).getCreateBy());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(activityList.get(i).getEditTime());
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(activityList.get(i).getEditBy());
                cell.setCellStyle(style);


            }




        }

        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName= URLEncoder.encode("市场活动", "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        wb.close();
        os.close();
    }

    //导入Excel文件
    @RequestMapping("/workbench/activity/Upfile.do")
    public @ResponseBody Object Upfile(MultipartFile myFile,String userName,HttpSession session){
        User user  =(User) session.getAttribute(Contants.SESSION_USER);
        Map<String,Object> map = new HashMap<>();
        List<Activity> activityList = new ArrayList<>();
        HSSFWorkbook wb = null;
        try {
            InputStream is = myFile.getInputStream();
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row =null;
        HSSFCell cell=null;

        for (int i = 1; i <=sheet.getLastRowNum(); i++) {
            row=sheet.getRow(i);
            Activity activity = new Activity();
            //id, owner, name, start_date, end_date, cost, description, create_time, create_by
            //          name	start_date	end_date	cost	description
            activity.setId(UUIDUtils.getUUID());
            activity.setOwner(user.getId());
            activity.setCreateTime(DateUtils.formatDateTime(new Date()));
            activity.setCreateBy(user.getId());
            for (int j = 0; j <row.getLastCellNum() ; j++) {
                cell=row.getCell(j);
              String cellValue=  getCellValue(cell);
              if(j==0){
                  activity.setName(cellValue);
              }else if(j==1){
                  activity.setStartDate(cellValue);
              }else if(j==2){
                  activity.setEndDate(cellValue);

              }else if(j==3){
                  activity.setCost(cellValue);

              }else {
                  activity.setDescription(cellValue);
              }

            }
            activityList.add(activity);


        }
        int result = activityService.saveActivityByList(activityList);
        if(result>0){
            map.put("code", Contants.RETURN_OBJECT_CODE_SUCCESS);
            map.put("count", result);

        }else {
            map.put("message", "文件导入失败！");
        }


        return  map;
    }

    private String getCellValue(HSSFCell cell) {
        String result="";
        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_STRING:
                result=cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result=cell.getBooleanCellValue()+"";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result=cell.getCellFormula()+"";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                result=cell.getNumericCellValue()+"";
                break;
            default:
                result="";

        }
        return result;
    }

    @RequestMapping("/workbench/controller/todetail.do")
    public String todetail(String id,Model model){
        System.out.println("============================>"+id);
        Activity activity = activityService.queryActivityDetailById(id);
        model.addAttribute("activity", activity);
        List<ActivityRemark> remarkList = activityRemarkService.queryByactivityId(id);
        model.addAttribute("remarkList",remarkList);

        return "workbench/activity/detail";
    }
}

































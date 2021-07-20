package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/7/3 0003
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;



    @Override
    public int saveClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueById(String id) {

        return clueMapper.selectClueById(id);
    }


    @Override
    public void toConvert(Map<String, Object> map) {
        User user = (User) map.get("user");
        String isCreateTran = (String) map.get("isCreateTran");
        //(1)获取到线索id,通过线索id获取线索对象（线索对象当中封装了线索的信息）
        String clueId = (String) map.get("clueId");
        Clue clue = clueMapper.selectClueById(clueId);

        //(2)通过线索对象提取客户信息，保存客户表（公司）
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setAddress(clue.getAddress());
        customer.setDescription(clue.getDescription());
        customer.setNextContactTime(clue.getNextContactTime());
        int result1 = customerMapper.insertCustomer(customer);
        System.out.println("======================>" + result1);

        //(3)通过线索对象提取联系人信息，保存联系人（个人）
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int result2 = contactsMapper.insertContacts(contacts);
        System.out.println("======================>" + result2);

/*
* 1）线索转换页面的处理
（1）跳转到线索转换页面
（2）处理线索转换页面基本信息
（3）处理线索转换页面，关于交易表单中的信息
2）实现线索转换的操作
（1）获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
（2）通过线索对象提取客户信息,保存客户表
（3）通过线索对象提取联系人信息，保存联系人
（4）线索备注转换到客户备注以及联系人备注
（5）“线索和市场活动”的关系转换到“联系人和市场活动”的关系
（6）如果有创建交易需求
（7）创建一条交易,还要将线索下的备注转到交易备注
（8） 删除线索备注
（9）删除线索和市场活动的关系
（10）删除线索
* */
        //（4） 线索备注转换到客户备注以及联系人备注

        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);

        if (clueRemarkList != null && clueRemarkList.size() > 0) {
            List<CustomerRemark> customerRemarkList = new ArrayList<>();
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();
            for (ClueRemark cr : clueRemarkList) {
                ContactsRemark contactsRemark = new ContactsRemark();
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtils.getUUID());
                customerRemark.setNoteContent(cr.getNoteContent());
                customerRemark.setCreateBy(user.getId());
                customerRemark.setCreateTime(cr.getCreateTime());
                customerRemark.setCustomerId(customer.getId());
                customerRemarkList.add(customerRemark);

                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setNoteContent(cr.getNoteContent());
                contactsRemark.setCreateBy(user.getId());
                contactsRemark.setCreateTime(cr.getCreateTime());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemarkList.add(contactsRemark);

            }
            int customerResult = customerRemarkMapper.insertCustomerRemark(customerRemarkList);
            int contactsResult = contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
            System.out.println("=================>contactsResult=" + contactsResult);
            System.out.println("=================>customerResult=" + customerResult);
        }

            //（5）“线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        if(clueActivityRelationList != null && clueActivityRelationList.size()>0){
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
               contactsActivityRelation.setId(UUIDUtils.getUUID());
               contactsActivityRelation.setContactsId(contacts.getId());
               contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
               contactsActivityRelationList.add(contactsActivityRelation);
            }
            int contactsActivityRelationResult = contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
            System.out.println("=======================>contactsActivityRelationResult="+contactsActivityRelationResult);

        }

        //（6）如果有创建交易需求
        //（7）创建一条交易,还要将线索下的备注转到交易备注
        Tran tran =null;
        if (isCreateTran != null && "true".equals(isCreateTran)) {
            tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney((String) map.get("amountOfMoney"));
            tran.setName((String) map.get("tradeName"));
            tran.setExpectedDate((String) map.get("expectedClosingDate"));
            tran.setCustomerId(customer.getId());
            tran.setStage((String) map.get("stage"));
            tran.setSource(clue.getSource());
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());
            int tranResult = tranMapper.insertTran(tran);
            System.out.println("===========================>tranResult="+tranResult);


        }
        if (isCreateTran != null && "true".equals(isCreateTran)) {
            //（7）创建一条交易,还要将线索下的备注转到交易备注
            if (clueRemarkList != null && clueRemarkList.size() > 0) {
                List<TranRemark> tranRemarkList = new ArrayList<>();

                for (ClueRemark clueRemark : clueRemarkList) {
                    TranRemark tranRemark = new TranRemark();

                    tranRemark.setId(UUIDUtils.getUUID());
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemark.setCreateBy(clueRemark.getCreateBy());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());

                        tranRemark.setTranId(tran.getId());


                    tranRemarkList.add(tranRemark);
                }
                int tranRemarkListResult = tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
                System.out.println("===================>tranRemarkListResult="+tranRemarkListResult);

            }
        }

    }
    /*
       （8） 删除线索备注
       （9）删除线索和市场活动的关系
       （10）删除线索
    * */
}

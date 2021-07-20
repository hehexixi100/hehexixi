package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.StageAndCount;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 唐坤
 * 2021/7/8 0008
 */
@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public int saveTranAndMaybeSaveCustomer(Map<String, Object> map) {
        User user  =(User) map.get("user");
        Tran tran = (Tran) map.get("tran");
        String  customerName = (String) map.get("customerName");
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        String customerId = tran.getCustomerId();

        if(customerId == null || customerId.trim().length()==0){
            Customer customer = new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setCreateBy(user.getId());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));

            //向数据库添加公司用户记录
            int insertCustomerResult = customerMapper.insertCustomer(customer);
            tran.setCustomerId(customer.getId());
            System.out.println("===================insertCustomerResult=>"+insertCustomerResult);

        }
        int insertTranResult = tranMapper.insertTran(tran);
        System.out.println("===================insertTranResult>" + insertTranResult);


        return 0;
    }

    @Override
    public Tran queryTranById(String id) {
        return tranMapper.selectTranById(id);
    }

    @Override
    public List<StageAndCount> queryStageAndCount() {
        return tranMapper.selectStageAndCount();
    }
}

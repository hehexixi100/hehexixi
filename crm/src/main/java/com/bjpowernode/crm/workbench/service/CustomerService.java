package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

/**
 * 唐坤
 * 2021/7/7 0007
 */
public interface CustomerService {
    //根据客户(公司)名称模糊查询
    List<Customer> queryCustomerByName(String name);
}

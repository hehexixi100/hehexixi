package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 唐坤
 * 2021/7/3 0003
 */
public interface ClueService {

    //增加线索
    int saveClue(Clue clue);

    //根据线索ID查询
    Clue queryClueById(String id);

    void toConvert(Map<String, Object> map);
}

package com.ruoyi.service.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.domain.SbkLibraryRenew;

public interface LifeLibraryService {
    String token(); // 获取token

    JSONObject addreader(SbkLibraryReader sbkLibraryReader); // 新增读者

    JSONObject rdloanlist(String rdid); // 查询读者当前借阅（群）

    JSONObject renewbook(SbkLibraryRenew sbkLibraryRenew); // 续借（群）

    JSONObject searchreaderlist(String rdcertify); // 多条件查询读者列表（群）

    JSONObject search(String q, String searchWay, String page, String rows); // 图书检索接口

    JSONObject holding(String bookrecno); // 查询馆藏信息

    JSONObject book(String bookrecno); // 书目记录号获取书目信息接口
}

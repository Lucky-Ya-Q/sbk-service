package com.ruoyi.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.service.LifeLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "石家庄市图书馆")
@RestController
@RequestMapping("/life/library")
public class LifeLibraryController {
    @Autowired
    private LifeLibraryService lifeLibraryService;

    @ApiOperation("查询读者列表")
    @PostMapping("/searchreaderlist")
    public AjaxResult searchreaderlist(String rdcertify) {
        JSONObject jsonObject = lifeLibraryService.searchreaderlist(rdcertify);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("图书检索")
    @GetMapping("/search")
    public AjaxResult search(String q, String searchWay, String page, String rows) {
        JSONObject jsonObject = lifeLibraryService.search(q, searchWay, page, rows);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("查询馆藏信息")
    @GetMapping("/holding")
    public AjaxResult holding(String bookrecno) {
        JSONObject jsonObject = lifeLibraryService.holding(bookrecno);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("获取书目信息")
    @GetMapping("/book")
    public AjaxResult book(String bookrecno) {
        JSONObject jsonObject = lifeLibraryService.book(bookrecno);
        return AjaxResult.success(jsonObject);
    }
}

package com.ruoyi.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.domain.SbkLibraryRenew;
import com.ruoyi.service.service.ISbkLibraryReaderService;
import com.ruoyi.service.service.LifeLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "石家庄市图书馆")
@RestController
@RequestMapping("/life/library")
public class LifeLibraryController {
    @Autowired
    private LifeLibraryService lifeLibraryService;

    @ApiOperation("新增读者")
    @PostMapping("/addreader")
    public AjaxResult addreader(@RequestBody SbkLibraryReader sbkLibraryReader) {
        JSONObject jsonObject = lifeLibraryService.addreader(sbkLibraryReader);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("查询读者当前借阅")
    @PostMapping("/rdloanlist")
    public AjaxResult rdloanlist(String rdid) {
        JSONObject jsonObject = lifeLibraryService.rdloanlist(rdid);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("续借")
    @PostMapping("/renewbook")
    public AjaxResult renewbook(@RequestBody SbkLibraryRenew sbkLibraryRenew) {
        JSONObject jsonObject = lifeLibraryService.renewbook(sbkLibraryRenew);
        return AjaxResult.success(jsonObject);
    }

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

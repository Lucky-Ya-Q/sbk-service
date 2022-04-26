package com.ruoyi.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.domain.SbkIndexMenu;
import com.ruoyi.service.service.ISbkIndexMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "社保卡民生服务")
@RestController
@RequestMapping("/api/sbk/life")
public class SbkLifeController {
    @Autowired
    private ISbkIndexMenuService sbkIndexMenuService;

    /**
     * 查询首页菜单列表
     */
    @ApiOperation("查询首页菜单列表")
    @GetMapping("/menuList")
    public AjaxResult menuList() {
        List<SbkIndexMenu> list = sbkIndexMenuService.list(new LambdaQueryWrapper<SbkIndexMenu>()
                .ge(SbkIndexMenu::getVisible, "0"));
        return AjaxResult.success(list);
    }
}

package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.service.domain.SbkIndexMenu;
import com.ruoyi.service.domain.SbkScenicSpots;
import com.ruoyi.service.service.ISbkIndexMenuService;
import com.ruoyi.service.service.ISbkScenicSpotsService;
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
public class SbkLifeController extends BaseController {
    @Autowired
    private ISbkIndexMenuService sbkIndexMenuService;
    @Autowired
    private ISbkScenicSpotsService sbkScenicSpotsService;

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

    /**
     * 查询旅游景点列表
     *
     * @return
     */
    @ApiOperation("查询旅游景点列表")
    @GetMapping("/spotsList")
    public TableDataInfo spotsList(SbkScenicSpots sbkScenicSpots) {
        startPage();
        LambdaQueryWrapper<SbkScenicSpots> queryWrapper = new LambdaQueryWrapper<SbkScenicSpots>()
                .eq(StrUtil.isNotEmpty(sbkScenicSpots.getTitle()), SbkScenicSpots::getTitle, sbkScenicSpots.getTitle());
        List<SbkScenicSpots> list = sbkScenicSpotsService.list(queryWrapper);
        return getDataTable(list);
    }
}

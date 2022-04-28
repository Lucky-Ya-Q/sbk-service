package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.service.domain.SbkIndexMenu;
import com.ruoyi.service.service.ISbkIndexMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 首页菜单Controller
 *
 * @author lucky-ya-q
 * @date 2022-04-25
 */
@Api(tags = "首页菜单")
@RestController
@RequestMapping("/service/menu")
public class SbkIndexMenuController extends BaseController {
    @Autowired
    private ISbkIndexMenuService sbkIndexMenuService;

    /**
     * 查询首页菜单列表
     */
    @ApiOperation("查询首页菜单列表")
    @PreAuthorize("@ss.hasPermi('service:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkIndexMenu sbkIndexMenu) {
        startPage();
        LambdaQueryWrapper<SbkIndexMenu> queryWrapper = new LambdaQueryWrapper<SbkIndexMenu>()
                .like(StrUtil.isNotEmpty(sbkIndexMenu.getMenuName()), SbkIndexMenu::getMenuName, sbkIndexMenu.getMenuName());
        List<SbkIndexMenu> list = sbkIndexMenuService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出首页菜单列表
     */
    @ApiOperation("导出首页菜单列表")
    @PreAuthorize("@ss.hasPermi('service:menu:export')")
    @Log(title = "首页菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkIndexMenu sbkIndexMenu) {
        LambdaQueryWrapper<SbkIndexMenu> queryWrapper = new LambdaQueryWrapper<SbkIndexMenu>()
                .like(StrUtil.isNotEmpty(sbkIndexMenu.getMenuName()), SbkIndexMenu::getMenuName, sbkIndexMenu.getMenuName());
        List<SbkIndexMenu> list = sbkIndexMenuService.list(queryWrapper);
        ExcelUtil<SbkIndexMenu> util = new ExcelUtil<>(SbkIndexMenu.class);
        util.exportExcel(response, list, "首页菜单数据");
    }

    /**
     * 获取首页菜单详细信息
     */
    @ApiOperation("获取首页菜单详细信息")
    @PreAuthorize("@ss.hasPermi('service:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable("menuId") Long menuId) {
        return AjaxResult.success(sbkIndexMenuService.getById(menuId));
    }

    /**
     * 新增首页菜单
     */
    @ApiOperation("新增首页菜单")
    @PreAuthorize("@ss.hasPermi('service:menu:add')")
    @Log(title = "首页菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkIndexMenu sbkIndexMenu) {
        return toAjax(sbkIndexMenuService.save(sbkIndexMenu));
    }

    /**
     * 修改首页菜单
     */
    @ApiOperation("修改首页菜单")
    @PreAuthorize("@ss.hasPermi('service:menu:edit')")
    @Log(title = "首页菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkIndexMenu sbkIndexMenu) {
        return toAjax(sbkIndexMenuService.updateById(sbkIndexMenu));
    }

    /**
     * 删除首页菜单
     */
    @ApiOperation("删除首页菜单")
    @PreAuthorize("@ss.hasPermi('service:menu:remove')")
    @Log(title = "首页菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuIds}")
    public AjaxResult remove(@PathVariable Long[] menuIds) {
        return toAjax(sbkIndexMenuService.removeBatchByIds(Arrays.asList(menuIds)));
    }
}

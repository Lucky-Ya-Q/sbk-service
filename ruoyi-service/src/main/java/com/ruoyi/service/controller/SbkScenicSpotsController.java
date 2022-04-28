package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.service.domain.SbkScenicSpots;
import com.ruoyi.service.service.ISbkScenicSpotsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 旅游景点Controller
 *
 * @author lucky-ya-q
 * @date 2022-04-26
 */
@Api(tags = "旅游景点")
@RestController
@RequestMapping("/service/spots")
public class SbkScenicSpotsController extends BaseController {
    @Autowired
    private ISbkScenicSpotsService sbkScenicSpotsService;

    /**
     * 查询旅游景点列表
     */
    @ApiOperation("查询旅游景点列表")
    @PreAuthorize("@ss.hasPermi('service:spots:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkScenicSpots sbkScenicSpots) {
        startPage();
        LambdaQueryWrapper<SbkScenicSpots> queryWrapper = new LambdaQueryWrapper<SbkScenicSpots>()
                .eq(StrUtil.isNotEmpty(sbkScenicSpots.getTitle()), SbkScenicSpots::getTitle, sbkScenicSpots.getTitle());
        List<SbkScenicSpots> list = sbkScenicSpotsService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出旅游景点列表
     */
    @ApiOperation("导出旅游景点列表")
    @PreAuthorize("@ss.hasPermi('service:spots:export')")
    @Log(title = "旅游景点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkScenicSpots sbkScenicSpots) {
        LambdaQueryWrapper<SbkScenicSpots> queryWrapper = new LambdaQueryWrapper<SbkScenicSpots>()
                .eq(StrUtil.isNotEmpty(sbkScenicSpots.getTitle()), SbkScenicSpots::getTitle, sbkScenicSpots.getTitle());
        List<SbkScenicSpots> list = sbkScenicSpotsService.list(queryWrapper);
        ExcelUtil<SbkScenicSpots> util = new ExcelUtil<>(SbkScenicSpots.class);
        util.exportExcel(response, list, "旅游景点数据");
    }

    /**
     * 获取旅游景点详细信息
     */
    @ApiOperation("获取旅游景点详细信息")
    @PreAuthorize("@ss.hasPermi('service:spots:query')")
    @GetMapping(value = "/{spotsId}")
    public AjaxResult getInfo(@PathVariable("spotsId") Long spotsId) {
        return AjaxResult.success(sbkScenicSpotsService.getById(spotsId));
    }

    /**
     * 新增旅游景点
     */
    @ApiOperation("新增旅游景点")
    @PreAuthorize("@ss.hasPermi('service:spots:add')")
    @Log(title = "旅游景点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkScenicSpots sbkScenicSpots) {
        return toAjax(sbkScenicSpotsService.save(sbkScenicSpots));
    }

    /**
     * 修改旅游景点
     */
    @ApiOperation("修改旅游景点")
    @PreAuthorize("@ss.hasPermi('service:spots:edit')")
    @Log(title = "旅游景点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkScenicSpots sbkScenicSpots) {
        return toAjax(sbkScenicSpotsService.updateById(sbkScenicSpots));
    }

    /**
     * 删除旅游景点
     */
    @ApiOperation("删除旅游景点")
    @PreAuthorize("@ss.hasPermi('service:spots:remove')")
    @Log(title = "旅游景点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{spotsIds}")
    public AjaxResult remove(@PathVariable Long[] spotsIds) {
        return toAjax(sbkScenicSpotsService.removeBatchByIds(Arrays.asList(spotsIds)));
    }
}

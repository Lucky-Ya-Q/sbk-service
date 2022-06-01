package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.service.domain.SbkXbporder;
import com.ruoyi.service.service.ISbkXbporderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 西柏坡订单Controller
 *
 * @author lucky-ya-q
 * @date 2022-06-01
 */
@Api(tags = "西柏坡订单")
@RestController
@RequestMapping("/service/xbporder")
public class SbkXbporderController extends BaseController {
    @Autowired
    private ISbkXbporderService sbkXbporderService;

    /**
     * 查询西柏坡订单列表
     */
    @ApiOperation("查询西柏坡订单列表")
    @PreAuthorize("@ss.hasPermi('service:xbporder:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkXbporder sbkXbporder) {
        startPage();
        LambdaQueryWrapper<SbkXbporder> queryWrapper = new LambdaQueryWrapper<SbkXbporder>()
                .like(StrUtil.isNotEmpty(sbkXbporder.getName()), SbkXbporder::getName, sbkXbporder.getName())
                .eq(StrUtil.isNotEmpty(sbkXbporder.getPhone()), SbkXbporder::getPhone, sbkXbporder.getPhone())
                .eq(StrUtil.isNotEmpty(sbkXbporder.getIdCard()), SbkXbporder::getIdCard, sbkXbporder.getIdCard());
        List<SbkXbporder> list = sbkXbporderService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出西柏坡订单列表
     */
    @ApiOperation("导出西柏坡订单列表")
    @PreAuthorize("@ss.hasPermi('service:xbporder:export')")
    @Log(title = "西柏坡订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkXbporder sbkXbporder) {
        LambdaQueryWrapper<SbkXbporder> queryWrapper = new LambdaQueryWrapper<SbkXbporder>()
                .like(StrUtil.isNotEmpty(sbkXbporder.getName()), SbkXbporder::getName, sbkXbporder.getName())
                .eq(StrUtil.isNotEmpty(sbkXbporder.getPhone()), SbkXbporder::getPhone, sbkXbporder.getPhone())
                .eq(StrUtil.isNotEmpty(sbkXbporder.getIdCard()), SbkXbporder::getIdCard, sbkXbporder.getIdCard());
        List<SbkXbporder> list = sbkXbporderService.list(queryWrapper);
        ExcelUtil<SbkXbporder> util = new ExcelUtil<SbkXbporder>(SbkXbporder.class);
        util.exportExcel(response, list, "西柏坡订单数据");
    }

    /**
     * 获取西柏坡订单详细信息
     */
    @ApiOperation("获取西柏坡订单详细信息")
    @PreAuthorize("@ss.hasPermi('service:xbporder:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(sbkXbporderService.getById(orderId));
    }

    /**
     * 新增西柏坡订单
     */
    @ApiOperation("新增西柏坡订单")
    @PreAuthorize("@ss.hasPermi('service:xbporder:add')")
    @Log(title = "西柏坡订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkXbporder sbkXbporder) {
        return toAjax(sbkXbporderService.save(sbkXbporder));
    }

    /**
     * 修改西柏坡订单
     */
    @ApiOperation("修改西柏坡订单")
    @PreAuthorize("@ss.hasPermi('service:xbporder:edit')")
    @Log(title = "西柏坡订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkXbporder sbkXbporder) {
        return toAjax(sbkXbporderService.updateById(sbkXbporder));
    }

    /**
     * 删除西柏坡订单
     */
    @ApiOperation("删除西柏坡订单")
    @PreAuthorize("@ss.hasPermi('service:xbporder:remove')")
    @Log(title = "西柏坡订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(sbkXbporderService.removeBatchByIds(Arrays.asList(orderIds)));
    }
}

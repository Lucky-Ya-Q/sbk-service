package com.ruoyi.service.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.SbkLibraryRenew;
import com.ruoyi.service.service.ISbkLibraryRenewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 续借记录Controller
 *
 * @author lucky-ya-q
 * @date 2022-06-23
 */
@Api(tags = "续借记录")
@RestController
@RequestMapping("/service/renew")
public class SbkLibraryRenewController extends BaseController {
    @Autowired
    private ISbkLibraryRenewService sbkLibraryRenewService;

    /**
     * 查询续借记录列表
     */
    @ApiOperation("查询续借记录列表")
    @PreAuthorize("@ss.hasPermi('service:renew:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkLibraryRenew sbkLibraryRenew) {
        startPage();
        LambdaQueryWrapper<SbkLibraryRenew> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(sbkLibraryRenew.getRdid()), SbkLibraryRenew::getRdid, sbkLibraryRenew.getRdid());
        queryWrapper.like(StrUtil.isNotBlank(sbkLibraryRenew.getTitle()), SbkLibraryRenew::getTitle, sbkLibraryRenew.getTitle());
        List<SbkLibraryRenew> list = sbkLibraryRenewService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出续借记录列表
     */
    @ApiOperation("导出续借记录列表")
    @PreAuthorize("@ss.hasPermi('service:renew:export')")
    @Log(title = "续借记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkLibraryRenew sbkLibraryRenew) {
        LambdaQueryWrapper<SbkLibraryRenew> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(sbkLibraryRenew.getRdid()), SbkLibraryRenew::getRdid, sbkLibraryRenew.getRdid());
        queryWrapper.like(StrUtil.isNotBlank(sbkLibraryRenew.getTitle()), SbkLibraryRenew::getTitle, sbkLibraryRenew.getTitle());
        List<SbkLibraryRenew> list = sbkLibraryRenewService.list(queryWrapper);
        ExcelUtil<SbkLibraryRenew> util = new ExcelUtil<SbkLibraryRenew>(SbkLibraryRenew.class);
        util.exportExcel(response, list, "续借记录数据");
    }

    /**
     * 获取续借记录详细信息
     */
    @ApiOperation("获取续借记录详细信息")
    @PreAuthorize("@ss.hasPermi('service:renew:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sbkLibraryRenewService.getById(id));
    }

    /**
     * 新增续借记录
     */
    @ApiOperation("新增续借记录")
    @PreAuthorize("@ss.hasPermi('service:renew:add')")
    @Log(title = "续借记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkLibraryRenew sbkLibraryRenew) {
        return toAjax(sbkLibraryRenewService.save(sbkLibraryRenew));
    }

    /**
     * 修改续借记录
     */
    @ApiOperation("修改续借记录")
    @PreAuthorize("@ss.hasPermi('service:renew:edit')")
    @Log(title = "续借记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkLibraryRenew sbkLibraryRenew) {
        return toAjax(sbkLibraryRenewService.updateById(sbkLibraryRenew));
    }

    /**
     * 删除续借记录
     */
    @ApiOperation("删除续借记录")
    @PreAuthorize("@ss.hasPermi('service:renew:remove')")
    @Log(title = "续借记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sbkLibraryRenewService.removeBatchByIds(Arrays.asList(ids)));
    }
}

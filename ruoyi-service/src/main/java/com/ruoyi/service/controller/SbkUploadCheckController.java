package com.ruoyi.service.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.service.domain.SbkUploadBuka;
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
import com.ruoyi.service.domain.SbkUploadCheck;
import com.ruoyi.service.service.ISbkUploadCheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 检测上传记录Controller
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Api(tags = "检测上传记录")
@RestController
@RequestMapping("/service/check")
public class SbkUploadCheckController extends BaseController
{
    @Autowired
    private ISbkUploadCheckService sbkUploadCheckService;

    /**
     * 查询检测上传记录列表
     */
    @ApiOperation("查询检测上传记录列表")
    @PreAuthorize("@ss.hasPermi('service:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkUploadCheck sbkUploadCheck)
    {
        startPage();
        LambdaQueryWrapper<SbkUploadCheck> queryWrapper = new LambdaQueryWrapper<SbkUploadCheck>()
                .like(StrUtil.isNotEmpty(sbkUploadCheck.getFileName()), SbkUploadCheck::getFileName, sbkUploadCheck.getFileName());
        List<SbkUploadCheck> list = sbkUploadCheckService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出检测上传记录列表
     */
    @ApiOperation("导出检测上传记录列表")
    @PreAuthorize("@ss.hasPermi('service:check:export')")
    @Log(title = "检测上传记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkUploadCheck sbkUploadCheck)
    {
        LambdaQueryWrapper<SbkUploadCheck> queryWrapper = new LambdaQueryWrapper<SbkUploadCheck>()
                .like(StrUtil.isNotEmpty(sbkUploadCheck.getFileName()), SbkUploadCheck::getFileName, sbkUploadCheck.getFileName());
        List<SbkUploadCheck> list = sbkUploadCheckService.list(queryWrapper);
        ExcelUtil<SbkUploadCheck> util = new ExcelUtil<SbkUploadCheck>(SbkUploadCheck.class);
        util.exportExcel(response, list, "检测上传记录数据");
    }

    /**
     * 获取检测上传记录详细信息
     */
    @ApiOperation("获取检测上传记录详细信息")
    @PreAuthorize("@ss.hasPermi('service:check:query')")
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return AjaxResult.success(sbkUploadCheckService.getById(checkId));
    }

    /**
     * 新增检测上传记录
     */
    @ApiOperation("新增检测上传记录")
    @PreAuthorize("@ss.hasPermi('service:check:add')")
    @Log(title = "检测上传记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkUploadCheck sbkUploadCheck)
    {
        return toAjax(sbkUploadCheckService.save(sbkUploadCheck));
    }

    /**
     * 修改检测上传记录
     */
    @ApiOperation("修改检测上传记录")
    @PreAuthorize("@ss.hasPermi('service:check:edit')")
    @Log(title = "检测上传记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkUploadCheck sbkUploadCheck)
    {
        return toAjax(sbkUploadCheckService.updateById(sbkUploadCheck));
    }

    /**
     * 删除检测上传记录
     */
    @ApiOperation("删除检测上传记录")
    @PreAuthorize("@ss.hasPermi('service:check:remove')")
    @Log(title = "检测上传记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{checkIds}")
    public AjaxResult remove(@PathVariable Long[] checkIds)
    {
        return toAjax(sbkUploadCheckService.removeBatchByIds(Arrays.asList(checkIds)));
    }
}

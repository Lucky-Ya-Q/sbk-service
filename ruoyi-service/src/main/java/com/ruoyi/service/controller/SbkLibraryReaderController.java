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
import com.ruoyi.service.domain.SbkLibraryReader;
import com.ruoyi.service.service.ISbkLibraryReaderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 图书馆读者证Controller
 *
 * @author lucky-ya-q
 * @date 2022-06-22
 */
@Api(tags = "图书馆读者证")
@RestController
@RequestMapping("/service/reader")
public class SbkLibraryReaderController extends BaseController {
    @Autowired
    private ISbkLibraryReaderService sbkLibraryReaderService;

    /**
     * 查询图书馆读者证列表
     */
    @ApiOperation("查询图书馆读者证列表")
    @PreAuthorize("@ss.hasPermi('service:reader:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkLibraryReader sbkLibraryReader) {
        startPage();
        LambdaQueryWrapper<SbkLibraryReader> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sbkLibraryReader.getRdname()), SbkLibraryReader::getRdname, sbkLibraryReader.getRdname());
        queryWrapper.eq(StrUtil.isNotBlank(sbkLibraryReader.getRdcertify()), SbkLibraryReader::getRdcertify, sbkLibraryReader.getRdcertify());
        List<SbkLibraryReader> list = sbkLibraryReaderService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出图书馆读者证列表
     */
    @ApiOperation("导出图书馆读者证列表")
    @PreAuthorize("@ss.hasPermi('service:reader:export')")
    @Log(title = "图书馆读者证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkLibraryReader sbkLibraryReader) {
        LambdaQueryWrapper<SbkLibraryReader> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sbkLibraryReader.getRdname()), SbkLibraryReader::getRdname, sbkLibraryReader.getRdname());
        queryWrapper.eq(StrUtil.isNotBlank(sbkLibraryReader.getRdcertify()), SbkLibraryReader::getRdcertify, sbkLibraryReader.getRdcertify());
        List<SbkLibraryReader> list = sbkLibraryReaderService.list(queryWrapper);
        ExcelUtil<SbkLibraryReader> util = new ExcelUtil<SbkLibraryReader>(SbkLibraryReader.class);
        util.exportExcel(response, list, "图书馆读者证数据");
    }

    /**
     * 获取图书馆读者证详细信息
     */
    @ApiOperation("获取图书馆读者证详细信息")
    @PreAuthorize("@ss.hasPermi('service:reader:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sbkLibraryReaderService.getById(id));
    }

    /**
     * 新增图书馆读者证
     */
    @ApiOperation("新增图书馆读者证")
    @PreAuthorize("@ss.hasPermi('service:reader:add')")
    @Log(title = "图书馆读者证", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkLibraryReader sbkLibraryReader) {
        return toAjax(sbkLibraryReaderService.save(sbkLibraryReader));
    }

    /**
     * 修改图书馆读者证
     */
    @ApiOperation("修改图书馆读者证")
    @PreAuthorize("@ss.hasPermi('service:reader:edit')")
    @Log(title = "图书馆读者证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkLibraryReader sbkLibraryReader) {
        return toAjax(sbkLibraryReaderService.updateById(sbkLibraryReader));
    }

    /**
     * 删除图书馆读者证
     */
    @ApiOperation("删除图书馆读者证")
    @PreAuthorize("@ss.hasPermi('service:reader:remove')")
    @Log(title = "图书馆读者证", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sbkLibraryReaderService.removeBatchByIds(Arrays.asList(ids)));
    }
}

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
import com.ruoyi.service.domain.SbkEmsAddress;
import com.ruoyi.service.service.ISbkEmsAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 邮政地址簿Controller
 *
 * @author lucky-ya-q
 * @date 2022-06-30
 */
@Api(tags = "邮政地址簿")
@RestController
@RequestMapping("/service/address")
public class SbkEmsAddressController extends BaseController {
    @Autowired
    private ISbkEmsAddressService sbkEmsAddressService;

    /**
     * 查询邮政地址簿列表
     */
    @ApiOperation("查询邮政地址簿列表")
    @PreAuthorize("@ss.hasPermi('service:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkEmsAddress sbkEmsAddress) {
        startPage();
        LambdaQueryWrapper<SbkEmsAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sbkEmsAddress.getName()), SbkEmsAddress::getName, sbkEmsAddress.getName());
        queryWrapper.eq(StrUtil.isNotBlank(sbkEmsAddress.getPhone()), SbkEmsAddress::getPhone, sbkEmsAddress.getPhone());
        List<SbkEmsAddress> list = sbkEmsAddressService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出邮政地址簿列表
     */
    @ApiOperation("导出邮政地址簿列表")
    @PreAuthorize("@ss.hasPermi('service:address:export')")
    @Log(title = "邮政地址簿", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkEmsAddress sbkEmsAddress) {
        LambdaQueryWrapper<SbkEmsAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sbkEmsAddress.getName()), SbkEmsAddress::getName, sbkEmsAddress.getName());
        queryWrapper.eq(StrUtil.isNotBlank(sbkEmsAddress.getPhone()), SbkEmsAddress::getPhone, sbkEmsAddress.getPhone());
        List<SbkEmsAddress> list = sbkEmsAddressService.list(queryWrapper);
        ExcelUtil<SbkEmsAddress> util = new ExcelUtil<SbkEmsAddress>(SbkEmsAddress.class);
        util.exportExcel(response, list, "邮政地址簿数据");
    }

    /**
     * 获取邮政地址簿详细信息
     */
    @ApiOperation("获取邮政地址簿详细信息")
    @PreAuthorize("@ss.hasPermi('service:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sbkEmsAddressService.getById(id));
    }

    /**
     * 新增邮政地址簿
     */
    @ApiOperation("新增邮政地址簿")
    @PreAuthorize("@ss.hasPermi('service:address:add')")
    @Log(title = "邮政地址簿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkEmsAddress sbkEmsAddress) {
        return toAjax(sbkEmsAddressService.save(sbkEmsAddress));
    }

    /**
     * 修改邮政地址簿
     */
    @ApiOperation("修改邮政地址簿")
    @PreAuthorize("@ss.hasPermi('service:address:edit')")
    @Log(title = "邮政地址簿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkEmsAddress sbkEmsAddress) {
        return toAjax(sbkEmsAddressService.updateById(sbkEmsAddress));
    }

    /**
     * 删除邮政地址簿
     */
    @ApiOperation("删除邮政地址簿")
    @PreAuthorize("@ss.hasPermi('service:address:remove')")
    @Log(title = "邮政地址簿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sbkEmsAddressService.removeBatchByIds(Arrays.asList(ids)));
    }
}

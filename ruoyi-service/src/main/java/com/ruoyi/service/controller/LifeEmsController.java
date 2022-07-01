package com.ruoyi.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.SbkEmsAddress;
import com.ruoyi.service.service.ISbkEmsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "邮政寄递")
@RestController
@RequestMapping("/life/ems")
public class LifeEmsController extends BaseController {
    @Autowired
    private ISbkEmsAddressService sbkEmsAddressService;

    /**
     * 查询邮政地址簿列表
     */
    @ApiOperation("查询邮政地址簿列表")
    @PostMapping("/list")
    public TableDataInfo list(SbkEmsAddress sbkEmsAddress) {
        startPage();
        LambdaQueryWrapper<SbkEmsAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SbkEmsAddress::getSfzh, sbkEmsAddress.getSfzh());
        List<SbkEmsAddress> list = sbkEmsAddressService.list(queryWrapper);
        return getDataTable(list);
    }


    /**
     * 获取邮政地址簿详细信息
     */
    @ApiOperation("获取邮政地址簿详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sbkEmsAddressService.getById(id));
    }

    /**
     * 新增邮政地址簿
     */
    @ApiOperation("新增邮政地址簿")
    @PostMapping
    public AjaxResult add(@RequestBody SbkEmsAddress sbkEmsAddress) {
        return toAjax(sbkEmsAddressService.save(sbkEmsAddress));
    }

    /**
     * 修改邮政地址簿
     */
    @ApiOperation("修改邮政地址簿")
    @PutMapping
    public AjaxResult edit(@RequestBody SbkEmsAddress sbkEmsAddress) {
        return toAjax(sbkEmsAddressService.updateById(sbkEmsAddress));
    }

    /**
     * 删除邮政地址簿
     */
    @ApiOperation("删除邮政地址簿")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sbkEmsAddressService.removeBatchByIds(Arrays.asList(ids)));
    }
}

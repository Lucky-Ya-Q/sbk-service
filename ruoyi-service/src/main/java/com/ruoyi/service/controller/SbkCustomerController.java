package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.service.ISbkCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 客户信息Controller
 *
 * @author lucky-ya-q
 * @date 2022-04-28
 */
@Api(tags = "客户信息")
@RestController
@RequestMapping("/service/customer")
public class SbkCustomerController extends BaseController {
    @Autowired
    private ISbkCustomerService sbkCustomerService;

    /**
     * 查询客户信息列表
     */
    @ApiOperation("查询客户信息列表")
    @PreAuthorize("@ss.hasPermi('service:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkCustomer sbkCustomer) {
        startPage();
        LambdaQueryWrapper<SbkCustomer> queryWrapper = new LambdaQueryWrapper<SbkCustomer>()
                .eq(SbkCustomer::getBukaId, sbkCustomer.getBukaId())
                .like(StrUtil.isNotEmpty(sbkCustomer.getXm()), SbkCustomer::getXm, sbkCustomer.getXm())
                .like(StrUtil.isNotEmpty(sbkCustomer.getZjhm()), SbkCustomer::getZjhm, sbkCustomer.getZjhm());
        List<SbkCustomer> list = sbkCustomerService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @ApiOperation("导出客户信息列表")
    @PreAuthorize("@ss.hasPermi('service:customer:export')")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkCustomer sbkCustomer) {
        LambdaQueryWrapper<SbkCustomer> queryWrapper = new LambdaQueryWrapper<SbkCustomer>()
                .eq(SbkCustomer::getBukaId, sbkCustomer.getBukaId())
                .like(StrUtil.isNotEmpty(sbkCustomer.getXm()), SbkCustomer::getXm, sbkCustomer.getXm())
                .like(StrUtil.isNotEmpty(sbkCustomer.getZjhm()), SbkCustomer::getZjhm, sbkCustomer.getZjhm());
        List<SbkCustomer> list = sbkCustomerService.list(queryWrapper);
        ExcelUtil<SbkCustomer> util = new ExcelUtil<>(SbkCustomer.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @ApiOperation("获取客户信息详细信息")
    @PreAuthorize("@ss.hasPermi('service:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sbkCustomerService.getById(id));
    }

    /**
     * 新增客户信息
     */
    @ApiOperation("新增客户信息")
    @PreAuthorize("@ss.hasPermi('service:customer:add')")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkCustomer sbkCustomer) {
        return toAjax(sbkCustomerService.save(sbkCustomer));
    }

    /**
     * 修改客户信息
     */
    @ApiOperation("修改客户信息")
    @PreAuthorize("@ss.hasPermi('service:customer:edit')")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkCustomer sbkCustomer) {
        return toAjax(sbkCustomerService.updateById(sbkCustomer));
    }

    /**
     * 删除客户信息
     */
    @ApiOperation("删除客户信息")
    @PreAuthorize("@ss.hasPermi('service:customer:remove')")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sbkCustomerService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
     * 统计补卡数量
     */
    @GetMapping("/bukaCount/{bukaId}")
    public AjaxResult bukaCount(@PathVariable("bukaId") Long bukaId) {
        Map<String, Integer> map = sbkCustomerService.bukaCount(bukaId);
        return AjaxResult.success(map);
    }

    /**
     * 补卡
     */
    @PostMapping("/buka")
    public AjaxResult buka() throws IOException {
        sbkCustomerService.buka();
        return AjaxResult.success();
    }
}

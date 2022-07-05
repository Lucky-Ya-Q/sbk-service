package com.ruoyi.service.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.service.domain.SbkEmsorder;
import com.ruoyi.service.service.ISbkEmsorderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 邮政订单Controller
 * 
 * @author lucky-ya-q
 * @date 2022-07-01
 */
@Api(tags = "邮政订单")
@RestController
@RequestMapping("/service/emsorder")
public class SbkEmsorderController extends BaseController
{
    @Autowired
    private ISbkEmsorderService sbkEmsorderService;

    /**
     * 查询邮政订单列表
     */
    @ApiOperation("查询邮政订单列表")
    @PreAuthorize("@ss.hasPermi('service:emsorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkEmsorder sbkEmsorder)
    {
        startPage();
        List<SbkEmsorder> list = sbkEmsorderService.list(null);
        return getDataTable(list);
    }

    /**
     * 导出邮政订单列表
     */
    @ApiOperation("导出邮政订单列表")
    @PreAuthorize("@ss.hasPermi('service:emsorder:export')")
    @Log(title = "邮政订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkEmsorder sbkEmsorder)
    {
        List<SbkEmsorder> list = sbkEmsorderService.list(null);
        ExcelUtil<SbkEmsorder> util = new ExcelUtil<SbkEmsorder>(SbkEmsorder.class);
        util.exportExcel(response, list, "邮政订单数据");
    }

    /**
     * 获取邮政订单详细信息
     */
    @ApiOperation("获取邮政订单详细信息")
    @PreAuthorize("@ss.hasPermi('service:emsorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sbkEmsorderService.getById(id));
    }

    /**
     * 新增邮政订单
     */
    @ApiOperation("新增邮政订单")
    @PreAuthorize("@ss.hasPermi('service:emsorder:add')")
    @Log(title = "邮政订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkEmsorder sbkEmsorder)
    {
        return toAjax(sbkEmsorderService.save(sbkEmsorder));
    }

    /**
     * 修改邮政订单
     */
    @ApiOperation("修改邮政订单")
    @PreAuthorize("@ss.hasPermi('service:emsorder:edit')")
    @Log(title = "邮政订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkEmsorder sbkEmsorder)
    {
        return toAjax(sbkEmsorderService.updateById(sbkEmsorder));
    }

    /**
     * 删除邮政订单
     */
    @ApiOperation("删除邮政订单")
    @PreAuthorize("@ss.hasPermi('service:emsorder:remove')")
    @Log(title = "邮政订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sbkEmsorderService.removeBatchByIds(Arrays.asList(ids)));
    }
}

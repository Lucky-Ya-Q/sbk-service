package com.ruoyi.service.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.service.domain.SbkCustomer;
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
import com.ruoyi.service.domain.SbkUploadBuka;
import com.ruoyi.service.service.ISbkUploadBukaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 补卡上传记录Controller
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Api(tags = "补卡上传记录")
@RestController
@RequestMapping("/service/buka")
public class SbkUploadBukaController extends BaseController
{
    @Autowired
    private ISbkUploadBukaService sbkUploadBukaService;

    /**
     * 查询补卡上传记录列表
     */
    @ApiOperation("查询补卡上传记录列表")
    @PreAuthorize("@ss.hasPermi('service:buka:list')")
    @GetMapping("/list")
    public TableDataInfo list(SbkUploadBuka sbkUploadBuka)
    {
        startPage();
        LambdaQueryWrapper<SbkUploadBuka> queryWrapper = new LambdaQueryWrapper<SbkUploadBuka>()
                .like(StrUtil.isNotEmpty(sbkUploadBuka.getFileName()), SbkUploadBuka::getFileName, sbkUploadBuka.getFileName())
                .orderByDesc(SbkUploadBuka::getCreateTime);
        List<SbkUploadBuka> list = sbkUploadBukaService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 导出补卡上传记录列表
     */
    @ApiOperation("导出补卡上传记录列表")
    @PreAuthorize("@ss.hasPermi('service:buka:export')")
    @Log(title = "补卡上传记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SbkUploadBuka sbkUploadBuka)
    {
        LambdaQueryWrapper<SbkUploadBuka> queryWrapper = new LambdaQueryWrapper<SbkUploadBuka>()
                .like(StrUtil.isNotEmpty(sbkUploadBuka.getFileName()), SbkUploadBuka::getFileName, sbkUploadBuka.getFileName())
                .orderByDesc(SbkUploadBuka::getCreateTime);
        List<SbkUploadBuka> list = sbkUploadBukaService.list(queryWrapper);
        ExcelUtil<SbkUploadBuka> util = new ExcelUtil<SbkUploadBuka>(SbkUploadBuka.class);
        util.exportExcel(response, list, "补卡上传记录数据");
    }

    /**
     * 获取补卡上传记录详细信息
     */
    @ApiOperation("获取补卡上传记录详细信息")
    @PreAuthorize("@ss.hasPermi('service:buka:query')")
    @GetMapping(value = "/{bukaId}")
    public AjaxResult getInfo(@PathVariable("bukaId") Long bukaId)
    {
        return AjaxResult.success(sbkUploadBukaService.getById(bukaId));
    }

    /**
     * 新增补卡上传记录
     */
    @ApiOperation("新增补卡上传记录")
    @PreAuthorize("@ss.hasPermi('service:buka:add')")
    @Log(title = "补卡上传记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SbkUploadBuka sbkUploadBuka)
    {
        return toAjax(sbkUploadBukaService.save(sbkUploadBuka));
    }

    /**
     * 修改补卡上传记录
     */
    @ApiOperation("修改补卡上传记录")
    @PreAuthorize("@ss.hasPermi('service:buka:edit')")
    @Log(title = "补卡上传记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SbkUploadBuka sbkUploadBuka)
    {
        return toAjax(sbkUploadBukaService.updateById(sbkUploadBuka));
    }

    /**
     * 删除补卡上传记录
     */
    @ApiOperation("删除补卡上传记录")
    @PreAuthorize("@ss.hasPermi('service:buka:remove')")
    @Log(title = "补卡上传记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bukaIds}")
    public AjaxResult remove(@PathVariable Long[] bukaIds)
    {
        sbkUploadBukaService.removeSbkCustomer(Arrays.asList(bukaIds));
        return AjaxResult.success();
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SbkCustomer> util = new ExcelUtil<>(SbkCustomer.class);
        util.importTemplateExcel(response, "用户数据");
    }

    @Log(title = "补卡上传记录", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('service:buka:add')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SbkCustomer> util = new ExcelUtil<>(SbkCustomer.class);
        List<SbkCustomer> sbkCustomerList = util.importExcel(file.getInputStream());
        String fileName = file.getOriginalFilename();
        sbkUploadBukaService.importSbkCustomer(sbkCustomerList, updateSupport, fileName);
        return AjaxResult.success();
    }
}

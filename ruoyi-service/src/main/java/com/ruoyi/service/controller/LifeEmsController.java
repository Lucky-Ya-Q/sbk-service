package com.ruoyi.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.SbkEmsAddress;
import com.ruoyi.service.domain.SbkEmsorder;
import com.ruoyi.service.dto.*;
import com.ruoyi.service.service.ISbkEmsAddressService;
import com.ruoyi.service.service.ISbkEmsorderService;
import com.ruoyi.service.service.LifeEmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(tags = "邮政寄递")
@RestController
@RequestMapping("/life/ems")
public class LifeEmsController extends BaseController {
    @Autowired
    private ISbkEmsAddressService sbkEmsAddressService;
    @Autowired
    private ISbkEmsorderService sbkEmsorderService;
    @Autowired
    private LifeEmsService lifeEmsService;

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

    /**
     * 新增邮政订单
     */
    @ApiOperation("新增邮政订单")
    @PostMapping("/addOrder")
    public AjaxResult addOrder(@RequestBody SbkEmsorder sbkEmsorder) {
        return toAjax(sbkEmsorderService.save(sbkEmsorder));
    }

    /**
     * 上门取件
     */
    @ApiOperation("上门取件")
    @PostMapping("/pl")
    public JSONObject pl(@RequestBody PlParam plParam) {
        return lifeEmsService.pl(plParam);
    }

    /**
     * 取消上门取件
     */
    @ApiOperation("取消上门取件")
    @PostMapping("/undoOrder")
    public JSONObject undoOrder(@RequestBody UndoOrderParam undoOrderParam) {
        return lifeEmsService.undoOrder(undoOrderParam);
    }

    /**
     * 我的订单
     */
    @ApiOperation("我的订单")
    @PostMapping("/myOrder")
    public AjaxResult myOrder(@RequestBody MyOrderParam myOrderParam) {
        return AjaxResult.success(lifeEmsService.myOrder(myOrderParam));
    }

    /**
     * 揽收结果查询
     */
    @ApiOperation("揽收结果查询")
    @GetMapping("/collectResultQuery")
    public JSONObject collectResultQuery(String txLogisticID) {
        return lifeEmsService.collectResultQuery(txLogisticID);
    }

    /**
     * 预估邮费
     */
    @ApiOperation("预估邮费")
    @PostMapping("/estimatepostage")
    public JSONObject estimatepostage(@RequestBody LogisticsInterface logisticsInterface) {
        return lifeEmsService.estimatepostage(logisticsInterface);
    }

    /**
     * 轨迹查询
     */
    @ApiOperation("轨迹查询")
    @GetMapping("/querytrace")
    public JSONObject querytrace(String waybillNo) {
        return lifeEmsService.querytrace(waybillNo);
    }

    /**
     * 派揽状态
     */
    @PostMapping("/worker")
    public EmsResult worker(@RequestBody EmsParam emsParam) {
        try {
            log.info("workerEmsParam ==> {}", emsParam);
            JSONObject requestParam = emsParam.getRequestParam();
            String txLogisticID = requestParam.getString("txLogisticID");
            LambdaUpdateWrapper<SbkEmsorder> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SbkEmsorder::getLogisticsOrderNo, txLogisticID);
            updateWrapper.set(SbkEmsorder::getWorker, requestParam.toJSONString());
            sbkEmsorderService.update(updateWrapper);
            return new EmsResult().setSuccess("T");
        } catch (Exception e) {
            return new EmsResult().setSuccess("F").setErrorMsg(e.getMessage());
        }
    }

    /**
     * 揽收结果
     */
    @PostMapping("/result")
    public EmsResult result(@RequestBody EmsParam emsParam) {
        try {
            log.info("resultEmsParam ==> {}", emsParam);
            JSONObject requestParam = emsParam.getRequestParam();
            String txLogisticID = requestParam.getString("txLogisticID");
            LambdaUpdateWrapper<SbkEmsorder> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SbkEmsorder::getLogisticsOrderNo, txLogisticID);
            updateWrapper.set(SbkEmsorder::getResult, requestParam.toJSONString());
            updateWrapper.set(SbkEmsorder::getStatus, requestParam.getString("status"));
            sbkEmsorderService.update(updateWrapper);
            return new EmsResult().setSuccess("T");
        } catch (Exception e) {
            return new EmsResult().setSuccess("F").setErrorMsg(e.getMessage());
        }
    }
}

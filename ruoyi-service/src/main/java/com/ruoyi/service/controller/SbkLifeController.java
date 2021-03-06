package com.ruoyi.service.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkIndexMenu;
import com.ruoyi.service.domain.SbkScenicSpots;
import com.ruoyi.service.domain.SbkXbporder;
import com.ruoyi.service.dto.AppointmentVO;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.dto.ZkjdcxParam;
import com.ruoyi.service.service.ISbkIndexMenuService;
import com.ruoyi.service.service.ISbkScenicSpotsService;
import com.ruoyi.service.service.ISbkXbporderService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.SbkParamUtils;
import com.tecsun.sm.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "社保卡民生服务")
@RestController
@RequestMapping("/api/sbk/life")
public class SbkLifeController extends BaseController {
    @Autowired
    private ISbkXbporderService sbkXbporderService;
    @Autowired
    private ISbkIndexMenuService sbkIndexMenuService;
    @Autowired
    private ISbkScenicSpotsService sbkScenicSpotsService;
    @Autowired
    private SbkService sbkService;

    /**
     * 查询首页菜单列表
     */
    @ApiOperation("查询首页菜单列表")
    @GetMapping("/menuList")
    public AjaxResult menuList() {
        List<SbkIndexMenu> list = sbkIndexMenuService.list(new LambdaQueryWrapper<SbkIndexMenu>()
                .ge(SbkIndexMenu::getVisible, "0"));
        return AjaxResult.success(list);
    }

    /**
     * 查询旅游景点列表
     *
     * @return
     */
    @ApiOperation("查询旅游景点列表")
    @GetMapping("/spotsList")
    public TableDataInfo spotsList(SbkScenicSpots sbkScenicSpots) {
        startPage();
        LambdaQueryWrapper<SbkScenicSpots> queryWrapper = new LambdaQueryWrapper<SbkScenicSpots>()
                .like(StrUtil.isNotEmpty(sbkScenicSpots.getTitle()), SbkScenicSpots::getTitle, sbkScenicSpots.getTitle());
        List<SbkScenicSpots> list = sbkScenicSpotsService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 卡应用状态
     *
     * @return
     */
    @ApiOperation("卡应用状态")
    @Log(title = "卡应用状态", businessType = BusinessType.OTHER)
    @PostMapping("/cardState")
    public AjaxResult cardState(ZkjdcxParam zkjdcxParam) throws IOException {
        // 社保卡基本信息查询
        Result result = sbkService.getResult("0811014", zkjdcxParam.getSfzh() + "||");
        if (!"200".equals(result.getStatusCode())) {
            throw new ServiceException(result.getMessage());
        }
        Map<String, String> data = (Map<String, String>) result.getData();
        String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
        String[] jbxxcxArr = jbxxcx.split("\\|");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", !jbxxcxArr[14].equals("2"));
        return AjaxResult.success(resultMap);
    }

    /**
     * 新增西柏坡订单
     *
     * @return
     */
    @ApiOperation("新增西柏坡订单")
    @Log(title = "新增西柏坡订单", businessType = BusinessType.OTHER)
    @PostMapping("/xbpOrder")
    public AjaxResult xbpOrder(@RequestBody AppointmentVO appointmentVO) {
        JSONObject decryptData = appointmentVO.getDecryptData();
        List<JSONObject> touristInfos = appointmentVO.getTouristInfos();

        SbkXbporder sbkXbporder = new SbkXbporder();
        sbkXbporder.setOrderId(appointmentVO.getOrderId());
        sbkXbporder.setName(decryptData.getString("name"));
        sbkXbporder.setPhone(decryptData.getString("phone"));
        sbkXbporder.setIdCard(decryptData.getString("idCard"));
        sbkXbporder.setTouristInfos(JSON.toJSONString(touristInfos));
        sbkXbporder.setAdmissionDate(appointmentVO.getAdmissionDate());
        sbkXbporder.setAdmissionPeriod(appointmentVO.getAdmissionPeriod());
        sbkXbporder.setTicketPrice(appointmentVO.getTicketPrice());
        sbkXbporder.setTicketsPurchasedName(appointmentVO.getTicketsPurchasedName());
        sbkXbporder.setCreateTime(new Date());
        sbkXbporderService.save(sbkXbporder);

        return AjaxResult.success();
    }

    /**
     * 核销西柏坡订单
     *
     * @return
     */
    @ApiOperation("核销西柏坡订单")
    @Log(title = "核销西柏坡订单", businessType = BusinessType.OTHER)
    @GetMapping("/cancelOrder")
    public AjaxResult cancelOrder(String orderId) {
        LambdaQueryWrapper<SbkXbporder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SbkXbporder::getOrderId, orderId);
        SbkXbporder sbkXbporder = sbkXbporderService.getOne(queryWrapper);
        if (sbkXbporder != null) {
            sbkXbporder.setUpdateTime(new Date());
            sbkXbporderService.updateById(sbkXbporder);
        }
        return AjaxResult.success();
    }
}

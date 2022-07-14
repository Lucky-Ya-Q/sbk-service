package com.ruoyi.service.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Maps;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.service.domain.SbkEmsAddress;
import com.ruoyi.service.domain.SbkEmsorder;
import com.ruoyi.service.dto.LogisticsInterface;
import com.ruoyi.service.dto.MyOrderParam;
import com.ruoyi.service.dto.PlParam;
import com.ruoyi.service.dto.UndoOrderParam;
import com.ruoyi.service.service.ISbkEmsorderService;
import com.ruoyi.service.service.LifeEmsService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LifeEmsServiceImpl implements LifeEmsService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISbkEmsorderService sbkEmsorderService;
    private final String baseUrl = "http://dingzhou.sjzydrj.net/jdpt/jdpt-order-pus-web";
    //    private final String url = "http://211.156.195.180/eis-itf-webext/uat_interface";
    private final String url = "http://dingzhou.sjzydrj.net/eis/eis-itf-webext/interface";
    //    private final String secrect = "5abb3d66d4d36c62d7b7040f422d7529";
    private final String secrect = "5510c07e82fbc58649e1f347c24734bf";
    private final String senderNo = "1100031465304";

    @Override
    public JSONObject pl(PlParam plParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String logisticsOrderNo = "DZSBKMSFW" + System.currentTimeMillis();

        JSONObject body = getSignJSONObject(logisticsOrderNo);
        body.put("logistics_order_no", logisticsOrderNo);
        // 授权码由邮政提供
        body.put("authorization", "PAILANTEST1592188060,PAILANTEST");
        // 1：文件  3、物品  2：信函  4、包裹
        body.put("contents_attribute", plParam.getContentsAttribute());
        // 1：特快专递  2：快递包裹  3：代收/到付
        body.put("biz_product_no", 1);
        // 订单重量  单位：千克
        body.put("weight", plParam.getWeight());
        // 1:客户自提2:上门投递3:智能包裹柜4:网点代投
        body.put("deliver_type", plParam.getDeliverType());
        // 1:寄件人 2:收件人 4:收件人集中付费
        body.put("payment_mode", plParam.getPaymentMode());
        // 1.否，2.小号纸箱，3.中号纸箱，4.大号纸箱，5.中号塑料袋，6.大号塑料袋，7.特大号塑料袋，8.填充物。
        body.put("daibaozhuang", plParam.getDaibaozhuang());
        // 预约时间  MM-dd HH:mm:ss至MM-dd HH:mm:ss
        body.put("createdTime", plParam.getCreatedTime());
        // 0 无固定协议客户代码 1有固定协议客户代码
        body.put("sender_type", 1);
        // 协议客户代码
        body.put("sender_no", senderNo);
        // 留言
        body.put("message", plParam.getMessage());
        // （1、散户-及时上门2、协议客户-固定频次上门按照机构配置时间分上下午推送，未维护默认上午9:00和下午16:00两个频次）
        body.put("pushMethod", 1);
        body.put("sender", plParam.getSender());
        body.put("receiver", plParam.getReceiver());
        JSONObject delivery = new JSONObject();
        delivery.put("prov", "安徽省");
        delivery.put("city", "合肥市");
        delivery.put("county", "巢湖市");
        delivery.put("address", "安徽省合肥市巢湖市盐务管理局");
        body.put("delivery", delivery);
        log.info(body.toJSONString());
        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        String result = restTemplate.postForObject(baseUrl + "/interface/receive/pl", entity, String.class);
        JSONObject jsonObject = JSON.parseObject(result);
        String status = jsonObject.getString("status");
        if (status.equals("T")) {
            log.info("保存订单信息……");
            SbkEmsorder sbkEmsorder = new SbkEmsorder();
            sbkEmsorder.setSfzh(plParam.getSfzh());
            sbkEmsorder.setMobile(plParam.getReceiver().getString("mobile"));
            sbkEmsorder.setStatus(2L);
            sbkEmsorder.setLogisticsOrderNo(logisticsOrderNo);
            sbkEmsorder.setContentsAttribute(plParam.getContentsAttribute());
            sbkEmsorder.setBizProductNo(body.getLong("biz_product_no"));
            sbkEmsorder.setWeight(plParam.getWeight());
            sbkEmsorder.setDeliverType(plParam.getContentsAttribute());
            sbkEmsorder.setPaymentMode(plParam.getPaymentMode());
            sbkEmsorder.setDaibaozhuang(plParam.getDaibaozhuang());
            sbkEmsorder.setSubTime(plParam.getCreatedTime());
            sbkEmsorder.setSenderType(body.getLong("sender_type"));
            sbkEmsorder.setSenderNo(senderNo);
            sbkEmsorder.setMessage(plParam.getMessage());
            sbkEmsorder.setPushMethod(body.getLong("pushMethod"));
            sbkEmsorder.setSender(plParam.getSender().toJSONString());
            sbkEmsorder.setReceiver(plParam.getReceiver().toJSONString());
            sbkEmsorder.setDelivery(delivery.toJSONString());
            sbkEmsorderService.save(sbkEmsorder);
        } else {
            String message = jsonObject.getString("message");
            log.info(message);
        }
        return jsonObject;
    }

    @Override
    public JSONObject undoOrder(UndoOrderParam undoOrderParam) {
        LambdaQueryWrapper<SbkEmsorder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SbkEmsorder::getSfzh, undoOrderParam.getSfzh());
        queryWrapper.eq(SbkEmsorder::getLogisticsOrderNo, undoOrderParam.getOrderNo());
        SbkEmsorder sbkEmsorder = sbkEmsorderService.getOne(queryWrapper);
        if (sbkEmsorder == null) {
            JSONObject fail = new JSONObject();
            fail.put("status", "F");
            fail.put("message", "订单不存在");
            fail.put("orderNo", undoOrderParam.getOrderNo());
            return fail;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = getSignJSONObject(undoOrderParam.getOrderNo());
        body.put("cancelCode", undoOrderParam.getCancelCode());
        body.put("orderNo", undoOrderParam.getOrderNo());
        body.put("authorization", "PAILANTEST1592188060");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        String result = restTemplate.postForObject(baseUrl + "/interface/receive/undoOrder", entity, String.class);
        JSONObject jsonObject = JSON.parseObject(result);
        String status = jsonObject.getString("status");
        if (status.equals("T")) {
            sbkEmsorder.setStatus(99L);
            sbkEmsorderService.updateById(sbkEmsorder);
        }
        return jsonObject;
    }

    @Override
    public JSONObject query() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = getSignJSONObject("logistics_rder_no");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        String result = restTemplate.postForObject(baseUrl + "/oms/pickupRange/query", entity, String.class);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject collectResultQuery(String txLogisticID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = getSignJSONObject(txLogisticID);
        body.put("txLogisticID", txLogisticID);
        body.put("authorization", "PAILANTEST1592188060");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        String result = restTemplate.postForObject(baseUrl + "/interface/receive/collectResultQuery", entity, String.class);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject updatePreTime() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = getSignJSONObject("202207091200");

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        String result = restTemplate.postForObject(baseUrl + "/customer/updatePreTime", entity, String.class);
        return JSON.parseObject(result);
    }

    @Override
    public List<SbkEmsorder> myOrder(MyOrderParam myOrderParam) {
        Long type = myOrderParam.getType();
        if (type == 1L) {
            LambdaQueryWrapper<SbkEmsorder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SbkEmsorder::getSfzh, myOrderParam.getSfzh());
            queryWrapper.eq(SbkEmsorder::getStatus, 2);
            List<SbkEmsorder> sbkEmsorderList = sbkEmsorderService.list(queryWrapper);
            for (SbkEmsorder sbkEmsorder : sbkEmsorderList) {
                JSONObject jsonObject = collectResultQuery(sbkEmsorder.getLogisticsOrderNo());
                sbkEmsorder.setStatus(jsonObject.getJSONObject("message").getLong("status"));
                sbkEmsorderService.updateById(sbkEmsorder);
            }
            return sbkEmsorderService.list(queryWrapper);
        } else if (type == 2L) {
            LambdaQueryWrapper<SbkEmsorder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SbkEmsorder::getSfzh, myOrderParam.getSfzh());
            queryWrapper.eq(SbkEmsorder::getStatus, 2);
            List<SbkEmsorder> sbkEmsorderList = sbkEmsorderService.list(queryWrapper);
            for (SbkEmsorder sbkEmsorder : sbkEmsorderList) {
                JSONObject jsonObject = collectResultQuery(sbkEmsorder.getLogisticsOrderNo());
                sbkEmsorder.setStatus(jsonObject.getJSONObject("message").getLong("status"));
                sbkEmsorderService.updateById(sbkEmsorder);
            }
            LambdaQueryWrapper<SbkEmsorder> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SbkEmsorder::getSfzh, myOrderParam.getSfzh());
            queryWrapper1.eq(SbkEmsorder::getStatus, 1);
            return sbkEmsorderService.list(queryWrapper1);
        } else if (type == 3L) {
            LambdaQueryWrapper<SbkEmsorder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SbkEmsorder::getMobile, myOrderParam.getMobile());
            queryWrapper.eq(SbkEmsorder::getStatus, 2);
            List<SbkEmsorder> sbkEmsorderList = sbkEmsorderService.list(queryWrapper);
            for (SbkEmsorder sbkEmsorder : sbkEmsorderList) {
                JSONObject jsonObject = collectResultQuery(sbkEmsorder.getLogisticsOrderNo());
                sbkEmsorder.setStatus(jsonObject.getJSONObject("message").getLong("status"));
                sbkEmsorderService.updateById(sbkEmsorder);
            }
            LambdaQueryWrapper<SbkEmsorder> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SbkEmsorder::getMobile, myOrderParam.getMobile());
            queryWrapper1.eq(SbkEmsorder::getStatus, 1);
            return sbkEmsorderService.list(queryWrapper1);
        } else {
            throw new ServiceException("类型错误");
        }
    }

    @Override
    public JSONObject querytrace(String waybillNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject logisticsInterface = new JSONObject();
        logisticsInterface.put("waybill_no", waybillNo);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("apiCode", "qps_querytrace");
        map.add("senderNo", senderNo);
        map.add("secrect", secrect);
        map.add("msgType", 0);
        map.add("serialNo", IdUtil.randomUUID());
        map.add("logistics_interface", logisticsInterface.toJSONString());
        String sha256Hex = DigestUtil.sha256Hex(logisticsInterface.toJSONString() + secrect);
        map.add("signature", Base64.encode(sha256Hex));
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        System.out.println(map);
        String result = restTemplate.postForObject(url, request, String.class);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject estimatepostage(LogisticsInterface logisticsInterface) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("apiCode", "bis_estimatepostage");
        map.add("senderNo", senderNo);
        map.add("secrect", secrect);
        map.add("msgType", 0);
        map.add("serialNo", IdUtil.randomUUID());
        map.add("logistics_interface", logisticsInterface.getLogisticsInterface().toJSONString());
        String sha256Hex = DigestUtil.sha256Hex(logisticsInterface.getLogisticsInterface().toJSONString() + secrect);
        map.add("signature", Base64.encode(sha256Hex));
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        System.out.println(map);
        String result = restTemplate.postForObject(url, request, String.class);
        return JSON.parseObject(result);
    }

    private static JSONObject getSignJSONObject(String logistics_order_no) {
        JSONObject jsonObject = new JSONObject();
        String salt = "19dd6c1cec504ac854645f352eb03209";
        String sign = HmacUtils.hmacMd5Hex(salt, logistics_order_no);
        jsonObject.put("sign", sign);
        log.info("签名 >>> {}", sign);
        return jsonObject;
    }

    public static void main(String[] args) {
        String sha256Hex = DigestUtil.sha256Hex("{\"waybill_no\":\"9892020120306\"}1");
        String encode = Base64.encode(sha256Hex);
        System.out.println(encode);
    }
}

package com.ruoyi.service.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.mapper.SbkCustomerMapper;
import com.ruoyi.service.service.ISbkCustomerService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.SbkParamUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.tecsun.sm.utils.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 客户信息Service业务层处理
 *
 * @author lucky-ya-q
 * @date 2022-04-28
 */
@Slf4j
@Service
public class SbkCustomerServiceImpl extends ServiceImpl<SbkCustomerMapper, SbkCustomer> implements ISbkCustomerService {
    @Autowired
    private SbkCustomerMapper sbkCustomerMapper;
    @Autowired
    private SbkService sbkService;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public Map<String, Integer> bukaCount(Long bukaId) {
        return sbkCustomerMapper.bukaCount(bukaId);
    }

    @Override
    public void buka() throws IOException {
        LambdaQueryWrapper<SbkCustomer> queryWrapper = new LambdaQueryWrapper<SbkCustomer>().eq(SbkCustomer::getSfybk, "W").last("limit 1");
        SbkCustomer sbkCustomer = sbkCustomerMapper.selectOne(queryWrapper);
        if (sbkCustomer == null) {
            throw new ServiceException("没有需要补卡的数据");
        }
        // 社保卡基本信息查询
        Result jbxxcxResult = sbkService.getResult("0811014", sbkCustomer.getZjhm() + "||");
        if (!"200".equals(jbxxcxResult.getStatusCode())) {
            throw new ServiceException(jbxxcxResult.getMessage());
        }
        Map<String, String> data = (Map<String, String>) jbxxcxResult.getData();
        String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));

        String[] jbxxcxArr = jbxxcx.split("\\|");

        String sbk_xb = sysDictDataMapper.selectDictLabel("sbk_xb", jbxxcxArr[3]);
        String sbk_mz = sysDictDataMapper.selectDictLabel("sbk_mz", jbxxcxArr[6]);
        int age = IdcardUtil.getAgeByIdCard(sbkCustomer.getZjhm());
        if (jbxxcxArr[0].equals(sbkCustomer.getXm()) && sbk_xb.equals(sbkCustomer.getXb()) && sbk_mz.equals(sbkCustomer.getMz()) && age > 16) {
            // 姓名，性别，民族相等并且年龄大于16周岁
            String uuid = IdUtil.simpleUUID(); // 业务单据号
            // 证件类型
            SysDictData sbk_zjlx = new SysDictData();
            sbk_zjlx.setDictType("sbk_zjlx");
            sbk_zjlx.setDictLabel(sbkCustomer.getZjzl());
            String zjlx = sysDictDataMapper.selectDictDataList(sbk_zjlx).get(0).getDictValue();
            // 证件有效期
            String zjyxq = sbkCustomer.getZjyxqkssj() + "-" + sbkCustomer.getZjyxqjssj();
            // 出生日期
            String birth = IdcardUtil.getBirthByIdCard(sbkCustomer.getZjhm());
            // 职业
            SysDictData sbk_zy = new SysDictData();
            sbk_zy.setDictType("sbk_zy");
            sbk_zy.setDictLabel(sbkCustomer.getZy());
            String zy = sysDictDataMapper.selectDictDataList(sbk_zy).get(0).getDictValue();
            // 手机
            String phone = jbxxcxArr[12];
            if (StrUtil.isNotEmpty(sbkCustomer.getLxfs())) {
                phone = sbkCustomer.getLxfs();
            }
            // 银行
            SysDictData sbk_yh = new SysDictData();
            sbk_yh.setDictType("sbk_yh");
            sbk_yh.setDictLabel(sbkCustomer.getZy());
            String yh = sysDictDataMapper.selectDictDataList(sbk_yh).get(0).getDictValue();
            // 补换卡申请
            String keyInfo = uuid + "|" + zjlx + "|" + jbxxcxArr[1] + "|" + jbxxcxArr[0] + "|2|1|" + zjyxq + "|" + jbxxcxArr[3] + "|" + birth + "|CHN|" + jbxxcxArr[6] + "|" + zy + "|" + sbkCustomer.getDz() + "|" + jbxxcxArr[8] + "|" + phone + "||||2||||||||999-130101|1|" + yh + "|||||";
            Result bhksqResult = sbkService.getResult("0821013", keyInfo);
            if (!"200".equals(bhksqResult.getStatusCode())) {
                throw new ServiceException(bhksqResult.getMessage());
            }
            log.info("补换卡申请：{}", bhksqResult);
            sbkCustomer.setSfybk("Y");
            sbkCustomerMapper.updateById(sbkCustomer);
        } else {
            sbkCustomer.setSfybk("N");
            sbkCustomerMapper.updateById(sbkCustomer);
        }
    }
}

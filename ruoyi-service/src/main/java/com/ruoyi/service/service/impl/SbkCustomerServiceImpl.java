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
import java.util.concurrent.locks.ReentrantLock;

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

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public Map<String, Integer> bukaCount(Long bukaId) {
        return sbkCustomerMapper.bukaCount(bukaId);
    }

    @Override
    public void buka(Long bukaId) {
        if (lock.tryLock()) {
            log.info("-------------  开始  -------------");
            try {
                LambdaQueryWrapper<SbkCustomer> queryWrapper = new LambdaQueryWrapper<SbkCustomer>()
                        .eq(SbkCustomer::getBukaId, bukaId)
                        .eq(SbkCustomer::getSfybk, "W").last("limit 1");
                SbkCustomer sbkCustomer = sbkCustomerMapper.selectOne(queryWrapper);
                if (sbkCustomer == null) {
                    throw new ServiceException("没有需要补卡的数据");
                }
                // 社保卡基本信息查询
                Result jbxxcxResult = sbkService.getResult("0811014", sbkCustomer.getZjhm() + "||");
                if (!"200".equals(jbxxcxResult.getStatusCode())) {
                    sbkCustomer.setSfybk("N");
                    sbkCustomer.setWbkyy(jbxxcxResult.getMessage());
                    sbkCustomerMapper.updateById(sbkCustomer);
                    throw new ServiceException(jbxxcxResult.getMessage());
                }
                Map<String, String> jbxxcxData = (Map<String, String>) jbxxcxResult.getData();
                String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, jbxxcxData.get("ReturnResult"));
                String[] jbxxcxArr = jbxxcx.split("\\|");

                String sbk_xb = sysDictDataMapper.selectDictLabel("sbk_xb", jbxxcxArr[3]);
                String sbk_mz = sysDictDataMapper.selectDictLabel("sbk_mz", jbxxcxArr[6]);
                int age = IdcardUtil.getAgeByIdCard(sbkCustomer.getZjhm());
                if (jbxxcxArr[0].equals(sbkCustomer.getXm()) && sbk_xb.equals(sbkCustomer.getXb()) && sbk_mz.equals(sbkCustomer.getMz()) && age > 16) {
                    // 姓名，性别，民族相等并且年龄大于16周岁
                    String uuid = IdUtil.simpleUUID(); // 业务单据号
                    // 证件类型
                    String zjlx = sysDictDataMapper.selectDictValue("sbk_zjlx", sbkCustomer.getZjzl());
                    if (StrUtil.isEmpty(zjlx)) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy("证件类型填写错误");
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException("证件类型填写错误");
                    }
                    // 证件有效期
                    String zjyxq = sbkCustomer.getZjyxqkssj() + "-" + sbkCustomer.getZjyxqjssj();
                    // 出生日期
                    String birth = IdcardUtil.getBirthByIdCard(sbkCustomer.getZjhm());
                    // 职业
                    String zy = sysDictDataMapper.selectDictValue("sbk_zy", sbkCustomer.getZy());
                    if (StrUtil.isEmpty(zy)) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy("职业填写错误");
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException("职业填写错误");
                    }
                    // 手机
                    String phone = jbxxcxArr[12];
                    if (StrUtil.isNotEmpty(sbkCustomer.getLxfs())) {
                        int flag = 0;
                        String[] lxfss = sbkCustomer.getLxfs().split("");
                        String[] phones = phone.split("");
                        if (lxfss.length == 11 && phones.length == 11) {
                            for (int i = 0; i < lxfss.length; i++) {
                                if (lxfss[i].equals(phones[i])) {
                                    flag++;
                                }
                            }
                        } else {
                            flag = 11;
                        }
                        if (flag < 10) {
                            phone = sbkCustomer.getLxfs();
                        }
                    }
                    // 银行
                    String yh = sysDictDataMapper.selectDictValue("sbk_yh", sbkCustomer.getYhmc());
                    if (StrUtil.isEmpty(yh)) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy("银行填写错误");
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException("银行填写错误");
                    }

                    // 办卡资格校验
                    Result bkzgjyResult = sbkService.getResult("0811011", jbxxcxArr[1] + "|" + jbxxcxArr[0] + "|2");
                    if (!"200".equals(bkzgjyResult.getStatusCode())) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy(bkzgjyResult.getMessage());
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException(bkzgjyResult.getMessage());
                    }
                    Map<String, String> bkzgjyData = (Map<String, String>) bkzgjyResult.getData();
                    String bkzgjy = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, bkzgjyData.get("ReturnResult"));
                    String[] bkzgjyArr = bkzgjy.split("\\|");
                    if (bkzgjyArr[0].equals("0")) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy("不可补卡");
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException("不可补卡");
                    }

                    // 补换卡申请
                    String keyInfo = uuid + "|" + zjlx + "|" + jbxxcxArr[1] + "|" + jbxxcxArr[0] + "|2|1|" + zjyxq + "|" + jbxxcxArr[3] + "|" + birth + "|CHN|" + jbxxcxArr[6] + "|" + zy + "|" + sbkCustomer.getDz() + "|" + jbxxcxArr[8] + "|" + phone + "|||" + bkzgjyArr[11] + "|1||||||||999-130101|1|" + yh + "|||||";
                    log.info("补换卡申请请求参数：{}", keyInfo);
                    Result bhksqResult = sbkService.getResult("0821013", keyInfo);
                    if (!"200".equals(bhksqResult.getStatusCode())) {
                        sbkCustomer.setSfybk("N");
                        sbkCustomer.setWbkyy(bhksqResult.getMessage());
                        sbkCustomerMapper.updateById(sbkCustomer);
                        throw new ServiceException(bhksqResult.getMessage());
                    }
                    Map<String, String> bhksqData = (Map<String, String>) bhksqResult.getData();
                    String bhksq = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, bhksqData.get("ReturnResult"));
                    log.info("补换卡申请返回结果：{}", bhksq);
                    sbkCustomer.setSfybk("Y");
                    sbkCustomerMapper.updateById(sbkCustomer);
                } else {
                    sbkCustomer.setSfybk("N");
                    sbkCustomer.setWbkyy("姓名/性别/民族与原始信息不一致或者年龄小于等于16周岁");
                    sbkCustomerMapper.updateById(sbkCustomer);
                    throw new ServiceException("姓名/性别/民族与原始信息不一致或者年龄小于等于16周岁");
                }
            } catch (IOException e) {
                throw new ServiceException(e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }
}

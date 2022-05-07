package com.ruoyi.service.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.domain.SbkUploadBuka;
import com.ruoyi.service.dto.Result;
import com.ruoyi.service.mapper.SbkCustomerMapper;
import com.ruoyi.service.mapper.SbkUploadBukaMapper;
import com.ruoyi.service.service.ISbkUploadBukaService;
import com.ruoyi.service.service.SbkService;
import com.ruoyi.service.util.SbkParamUtils;
import com.tecsun.sm.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 补卡上传记录Service业务层处理
 *
 * @author lucky-ya-q
 * @date 2022-04-27
 */
@Service
public class SbkUploadBukaServiceImpl extends ServiceImpl<SbkUploadBukaMapper, SbkUploadBuka> implements ISbkUploadBukaService {
    @Autowired
    private SbkUploadBukaMapper sbkUploadBukaMapper;
    @Autowired
    private SbkCustomerMapper sbkCustomerMapper;
    @Autowired
    private SbkService sbkService;

    @Override
    @Transactional
    public void importSbkCustomer(List<SbkCustomer> sbkCustomerList, boolean updateSupport, String fileName) throws IOException {
        fileName = FileNameUtil.mainName(fileName) + "-" + RandomUtil.randomNumbers(6) + "." + FileNameUtil.extName(fileName);
        SbkUploadBuka sbkUploadBuka = new SbkUploadBuka();
        sbkUploadBuka.setFileName(fileName);
        sbkUploadBukaMapper.insert(sbkUploadBuka);
        for (SbkCustomer sbkCustomer : sbkCustomerList) {
            sbkCustomer.setBukaId(sbkUploadBuka.getBukaId());
            sbkCustomer.setSfybk("W");
            sbkCustomerMapper.insert(sbkCustomer);

            // 社保卡基本信息查询
            Result jbxxcxResult = sbkService.getResult("0811014", sbkCustomer.getZjhm() + "||");
            if ("200".equals(jbxxcxResult.getStatusCode())) {
                Map<String, String> data = (Map<String, String>) jbxxcxResult.getData();
                String jbxxcx = ParamUtils.decrypted(SbkParamUtils.PRIVATEKEY, data.get("ReturnResult"));
                String[] jbxxcxArr = jbxxcx.split("\\|");
                // 正式挂失
                sbkService.getResult("0821017", jbxxcxArr[1] + "|" + jbxxcxArr[0] + "|" + jbxxcxArr[10]);
            }
        }
    }

    @Override
    @Transactional
    public void removeSbkCustomer(List<Long> bukaIdList) {
        sbkUploadBukaMapper.deleteBatchIds(bukaIdList);
        LambdaQueryWrapper<SbkCustomer> queryWrapper = new LambdaQueryWrapper<SbkCustomer>()
                .in(SbkCustomer::getBukaId, bukaIdList);
        sbkCustomerMapper.delete(queryWrapper);
    }
}

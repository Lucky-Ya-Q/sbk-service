package com.ruoyi.service.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.domain.SbkUploadBuka;
import com.ruoyi.service.mapper.SbkCustomerMapper;
import com.ruoyi.service.mapper.SbkUploadBukaMapper;
import com.ruoyi.service.service.ISbkUploadBukaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional
    public void importSbkCustomer(List<SbkCustomer> sbkCustomerList, boolean updateSupport, String fileName) {
        fileName = FileNameUtil.mainName(fileName) + "-" + RandomUtil.randomNumbers(6) + "." + FileNameUtil.extName(fileName);
        SbkUploadBuka sbkUploadBuka = new SbkUploadBuka();
        sbkUploadBuka.setFileName(fileName);
        sbkUploadBukaMapper.insert(sbkUploadBuka);
        for (SbkCustomer sbkCustomer : sbkCustomerList) {
            sbkCustomer.setBukaId(sbkUploadBuka.getBukaId());
            sbkCustomer.setSfybk("W");
            sbkCustomerMapper.insert(sbkCustomer);
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

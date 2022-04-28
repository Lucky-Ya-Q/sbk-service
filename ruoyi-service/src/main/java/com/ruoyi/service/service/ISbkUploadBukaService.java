package com.ruoyi.service.service;

import java.util.List;

import com.ruoyi.service.domain.SbkCustomer;
import com.ruoyi.service.domain.SbkUploadBuka;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 补卡上传记录Service接口
 * 
 * @author lucky-ya-q
 * @date 2022-04-27
 */
public interface ISbkUploadBukaService extends IService<SbkUploadBuka>
{
    void importSbkCustomer(List<SbkCustomer> sbkCustomerList, boolean updateSupport, String fileName);

    void removeSbkCustomer(List<Long> bukaIdList);
}

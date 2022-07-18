package com.ruoyi.service.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.service.domain.UnitinfoShi;
import com.ruoyi.service.mapper.UnitinfoShiMapper;
import com.ruoyi.service.service.IUnitinfoShiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单位信息Service业务层处理
 *
 * @author lucky-ya-q
 * @date 2022-03-17
 */
@Service
public class UnitinfoShiServiceImpl extends ServiceImpl<UnitinfoShiMapper, UnitinfoShi> implements IUnitinfoShiService {
    @Autowired
    private UnitinfoShiMapper unitinfoShiMapper;

    /**
     * 查询单位信息列表
     *
     * @param unitinfoShi 单位信息
     * @return 单位信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<UnitinfoShi> selectUnitinfoShiList(UnitinfoShi unitinfoShi) {
        return unitinfoShiMapper.selectUnitinfoShiList(unitinfoShi);
    }
}

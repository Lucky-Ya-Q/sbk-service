package com.ruoyi.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.service.domain.WxBukaBank;
import com.ruoyi.service.domain.WxMingzu;

import java.util.List;

/**
 * 银行信息Mapper接口
 *
 * @author lucky-ya-q
 * @date 2022-03-18
 */
public interface WxBukaBankMapper extends BaseMapper<WxBukaBank> {
    List<WxBukaBank> listAll();
}

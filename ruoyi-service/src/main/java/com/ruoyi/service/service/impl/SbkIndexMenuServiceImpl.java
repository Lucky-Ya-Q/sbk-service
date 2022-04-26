package com.ruoyi.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.domain.SbkIndexMenu;
import com.ruoyi.service.mapper.SbkIndexMenuMapper;
import com.ruoyi.service.service.ISbkIndexMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首页菜单Service业务层处理
 *
 * @author lucky-ya-q
 * @date 2022-04-25
 */
@Service
public class SbkIndexMenuServiceImpl extends ServiceImpl<SbkIndexMenuMapper, SbkIndexMenu> implements ISbkIndexMenuService {
    @Autowired
    private SbkIndexMenuMapper sbkIndexMenuMapper;
}

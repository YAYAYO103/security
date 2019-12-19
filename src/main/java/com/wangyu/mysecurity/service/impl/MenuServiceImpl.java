package com.wangyu.mysecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyu.mysecurity.entity.MenuEntity;
import com.wangyu.mysecurity.mapper.MenuMapper;
import com.wangyu.mysecurity.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

}

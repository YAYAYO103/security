package com.wangyu.mysecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyu.mysecurity.bean.dto.MenuTree;
import com.wangyu.mysecurity.entity.MenuEntity;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<MenuTree> selectMenuTree(Integer id);
}

package com.wangyu.mysecurity.bean.dto;

import com.wangyu.mysecurity.entity.MenuEntity;
import lombok.Data;

import java.util.List;

/**
 * @author YAYAYO
 * @description: 用户树形菜单
 * @date 2019.12.19 01917:23
 */
@Data
public class MenuTree extends MenuEntity {

    /**
     * 子菜单
     */
    private List<MenuEntity> children;

}

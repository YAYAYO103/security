package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.entity.RoleEntity;
import com.wangyu.mysecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YAYAYO
 * @description: 角色
 * @date 2019.12.25 02514:56
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * @description: 新增角色
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @PostMapping("/addRole")
    public R addRole(@RequestBody @Validated RoleEntity entity){
        return roleService.addRole(entity);
    }
}

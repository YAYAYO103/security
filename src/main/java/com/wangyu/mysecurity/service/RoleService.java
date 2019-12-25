package com.wangyu.mysecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.entity.RoleEntity;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
public interface RoleService extends IService<RoleEntity> {

    R addRole(RoleEntity entity);

}

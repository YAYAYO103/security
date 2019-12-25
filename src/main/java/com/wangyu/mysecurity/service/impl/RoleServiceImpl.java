package com.wangyu.mysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.entity.RoleEntity;
import com.wangyu.mysecurity.mapper.RoleMapper;
import com.wangyu.mysecurity.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    /**
     * @description: 新增角色
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Override
    public R addRole(RoleEntity entity) {
        //查询该角色名称是否存在
        Integer count = this.baseMapper.selectCount(new QueryWrapper<RoleEntity>()
                .eq("r_name", entity.getName().trim()));

        if(count>0){
            return R.error("该角色名称已经存在！");
        }

        entity.setName(entity.getName().trim());
        this.baseMapper.insert(entity);
        return R.ok("角色新增成功！");
    }
}

package com.wangyu.mysecurity.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyu.mysecurity.bean.dto.MenuTree;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.utils.CommentUtils;
import com.wangyu.mysecurity.comment.utils.RedisUtil;
import com.wangyu.mysecurity.entity.AccountEntity;
import com.wangyu.mysecurity.mapper.AccountMapper;
import com.wangyu.mysecurity.mapper.MenuMapper;
import com.wangyu.mysecurity.service.AccountService;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountEntity> implements AccountService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public R login(String username, String password) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return R.error("参数校验不通过！");
        }

        //根据用户名查询用户信息
        AccountEntity account = this.baseMapper.selectOne(new QueryWrapper<AccountEntity>()
                .eq("a_username", username)
                .last("limit 1"));

        if(account==null){
            return R.error("该用户不存在！");
        }

        //MD5加密后的密码对比
        password= SecureUtil.md5(password);
        if(!password.equals(account.getPassword())){
            return R.error("密码不正确！");
        }

        //登录成功

        //生成token
        String token= CommentUtils.createToken();
        //查询用户菜单信息
        List<MenuTree> menuTrees = menuMapper.selectMenuTree(account.getId());
        //存入redis
        RedisUtil.setExpireValue(CommentUtils.redisGetAccountInfo(token),JSONUtil.toJsonStr(account));
        //菜单存入redis
        RedisUtil.setExpireValue(CommentUtils.redisGetAccountMenus(token),JSONUtil.toJsonStr(menuTrees));

        String allToken = RedisUtil.get(Constants.REDIS_ALL_TOKEN+username);
        if(StringUtils.isEmpty(allToken)){
            //新存入 用于后面的用户踢出功能 用户名为key，token为value
            RedisUtil.setExpireValue(CommentUtils.redisGetAllToken(username),token);
        }else {
            //逗号拼接
            String value=allToken+","+token;
            RedisUtil.setExpireValue(CommentUtils.redisGetAllToken(username),value);
        }

        //封装返回参数
        Map<String,Object> result=new HashMap<>();
        result.put("token",token);
        result.put("menuTrees",menuTrees);

        return R.ok(result);
    }

    /**
     * 注销
     * @return
     */
    @Override
    public R logOut() {
        CommentUtils.redisAccountLogOut(CommentUtils.getToken());
        return R.ok("注销成功！");
    }

    /**
     * 新增账户
     * @param accountEntity
     * @return
     */
    @Override
    public R addAccount(AccountEntity accountEntity) {
        //根据账户名查询是否有这个账户信息
        Integer count = this.baseMapper.selectCount(new QueryWrapper<AccountEntity>()
                .eq("a_username", accountEntity.getUsername()));

        if(count>0){
            return R.error("该用户名已经存在！");
        }

        accountEntity.setId(null);
        this.baseMapper.insert(accountEntity);

        return R.ok("新增账户信息成功！");
    }

    /**
     * 编辑账户信息
     * @param accountEntity
     * @return
     */
    @Override
    public R updateAccount(AccountEntity accountEntity) {
        if(accountEntity.getId()==null){
            return R.error("id不能为空！");
        }
        //校验该id是否有数据
        Integer idCount = this.baseMapper.selectCount(new QueryWrapper<AccountEntity>()
                .eq("a_id", accountEntity.getId()));
        if(idCount<=0){
            return R.error("该条数据不存在！");
        }

        //md5加密
        String newPassword=SecureUtil.md5(accountEntity.getPassword());
        accountEntity.setPassword(newPassword);
        this.baseMapper.updateById(accountEntity);

        //删除对应缓存的用户信息
        CommentUtils.redisAccountLogOut(CommentUtils.getToken());

        return R.ok("数据更新成功！");
    }
}

package com.wangyu.mysecurity.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyu.mysecurity.bean.MenuTree;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.utils.CommentUtils;
import com.wangyu.mysecurity.comment.utils.RedisUtil;
import com.wangyu.mysecurity.entity.AccountEntity;
import com.wangyu.mysecurity.mapper.AccountMapper;
import com.wangyu.mysecurity.mapper.MenuMapper;
import com.wangyu.mysecurity.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

    @Value("${AES.key}")
    private String key;

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

        if(!password.equals(account.getAPassword())){
            return R.error("密码不正确！");
        }

        //登录成功

        //生成token
        String token= CommentUtils.createToken();
        //查询用户菜单信息
        List<MenuTree> menuTrees = menuMapper.selectMenuTree(account.getAId());
        //存入redis
        RedisUtil.set(token+Constants.REDIS_ACCOUNT_SUFFIX, JSONUtil.toJsonStr(account));
        RedisUtil.expire(token+Constants.REDIS_ACCOUNT_SUFFIX,Constants.REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
        //菜单存入redis
        RedisUtil.set(token+ Constants.REDIS_MENU_SUFFIX,JSONUtil.toJsonStr(menuTrees));
        RedisUtil.expire(token+ Constants.REDIS_MENU_SUFFIX,Constants.REDIS_EXPIRE_TIME,TimeUnit.SECONDS);

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
        String token = CommentUtils.getToken();
        //获取token为前缀的key
        Set<String> keys = RedisUtil.keys(token + "*");

        //清除缓存
        if(!CollectionUtils.isEmpty(keys)){
            keys.forEach(e->{
                RedisUtil.expire(e,0L, TimeUnit.SECONDS);
            });
        }
        return R.ok("注销成功！");
    }
}

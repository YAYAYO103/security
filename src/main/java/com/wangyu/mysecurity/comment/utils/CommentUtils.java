package com.wangyu.mysecurity.comment.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Strings;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.entity.AccountEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author YAYAYO
 * @description: 获取一些公共参数
 * @date 2019.12.19 01916:38
 */
public class CommentUtils {

    /**
     * 获取token
     * @return
     */
    public static String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //判断请求是否有token
        String token=request.getHeader(Constants.TOKEN);
        if(StringUtils.isEmpty(token)){
            token=request.getParameter(Constants.TOKEN);
        }

        //没有 直接拦截，提示用户登录过期
        if(StringUtils.isEmpty(token)){
            throw new RRException(ResultEnum.ExceptionEnum.NOT_LOGIN);
        }
        return token;
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    public static AccountEntity getAccount(){

        String token = CommentUtils.getToken();

        //有 查询用户身份信息
        String accountStr = RedisUtil.get(token+Constants.REDIS_ACCOUNT_SUFFIX);
        if(StringUtils.isEmpty(accountStr)){
            throw new RRException(ResultEnum.ExceptionEnum.NOT_LOGIN);
        }

        AccountEntity accountEntity = JSONUtil.toBean(accountStr, AccountEntity.class);
        return accountEntity;
    }

    /**
     * 生成token
     */
    public static String createToken(){
        return IdUtil.simpleUUID();
    }

    /*************************************缓存处理***********************************/

    /**
     * 获取账户的所有token
     * @param account
     * @return
     */
    public static String redisGetAllToken(String account){
        return Constants.REDIS_ALL_TOKEN+account;
    }

    /**
     * 获取该token对应的菜单信息
     * @param token
     * @return
     */
    public static String redisGetAccountMenus(String token){
        return token+Constants.REDIS_MENU_SUFFIX;
    }

    /**
     * 获取该token对应的用户信息
     * @param token
     * @return
     */
    public static String redisGetAccountInfo(String token){
        return token+Constants.REDIS_ACCOUNT_SUFFIX;
    }

    /**
     * 注销操作
     * @param
     * @return
     */
    public static boolean redisAccountLogOut(String token){
        if(RedisUtil.keys(token+"*").size()<=0){
            return true;
        }

        String account = RedisUtil.get(redisGetAccountInfo(token));
        if(StringUtils.isEmpty(account)){
            //没有账户信息 默认为redis中已经没有账户的相关消息了
            return true;
        }

        //获得账户名称
        AccountEntity entity=JSONUtil.toBean(account,AccountEntity.class);
        String accountName=entity.getUsername();

        //用户菜单过期
        RedisUtil.setExpire0L(redisGetAccountMenus(token));
        //用户信息过期
        RedisUtil.setExpire0L(redisGetAccountInfo(token));

        //获得账户的所有token
        String alltoken = RedisUtil.get(redisGetAllToken(accountName));
        String[] tokens = alltoken.split(",");

        //只有一个用户信息 不需要再重新组合token
        if(tokens.length<=1){
            RedisUtil.setExpire0L(CommentUtils.redisGetAllToken(accountName));
            return true;
        }

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(tokens));
        strings.remove(token);
        alltoken=String.join(",",strings);

        //获取剩余存活时间
        Long expire = RedisUtil.getExpire(redisGetAllToken(accountName));
        //存入redis
        RedisUtil.setExpireValue(CommentUtils.redisGetAllToken(accountName),alltoken,expire);

        return true;
    }
    /**
     * 踢出账户最早的token信息
     * @return
     */
    public static boolean redisKitFirstTokenInfo(String account){
        if(RedisUtil.keys(account+"*").size()<=0){
            return true;
        }

        //查询用户的最早的token
        String alltoken = RedisUtil.get(CommentUtils.redisGetAllToken(account));
        String[] tokens = alltoken.split(",");
        String firstToken=tokens[0];

        return redisAccountLogOut(firstToken);
    }

}

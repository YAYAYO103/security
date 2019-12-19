package com.wangyu.mysecurity.comment.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.entity.AccountEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        return IdUtil.randomUUID();
    }
}

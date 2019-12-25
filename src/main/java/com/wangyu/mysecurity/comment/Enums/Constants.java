package com.wangyu.mysecurity.comment.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constants {

    //请求中携带token名称
    public static final String TOKEN="token";
    //redis菜单后缀
    public static final String REDIS_MENU_SUFFIX=":menu";
    //redis账户后缀
    public static final String REDIS_ACCOUNT_SUFFIX=":account";
    //redis中同一个用户的token（逗号拼接）
    public static final String REDIS_ALL_TOKEN="all_token:";
    //redis数据的过期时间（秒）
    public static final Long REDIS_EXPIRE_TIME=7200L;

    //日志通用常量
    public static final String LOG_ACCOUNT_LOCK="冻结";
    public static final String LOG_ACCOUNT_UNLOCK="解冻";

    /**
     * 账户状态
     */
    @Getter
    @AllArgsConstructor
    public enum  AccountEnum{
        account_open(1,"账户正常"),
        account_Lock(2,"账户冻结"),
        ;
        private int code;
        private String msg;
    }

    /**
     * @description: 账户等级
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Getter
    @AllArgsConstructor
    public enum  AccountLevelEnum{
        IS_ADMIN(1,"超级管理员"),
        IS_NOT_ADMIN(2,"普通账户"),
        ;
        private int code;
        private String msg;
    }

    /**
     * @description: 日志解析方式
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Getter
    @AllArgsConstructor
    public enum  LogTypeEnum{
        //不需要处理  直接存入数据库
        NORMAL,
        //spel表达式解析
        SPEL,
        //threadLocal参数解析
        THREADLOCAL,
        ;
    }

}

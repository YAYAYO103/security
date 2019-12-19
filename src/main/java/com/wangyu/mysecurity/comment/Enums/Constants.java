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
    //redis数据的过期时间（秒）
    public static final Long REDIS_EXPIRE_TIME=7200L;

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

}

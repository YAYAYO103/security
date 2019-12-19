package com.wangyu.mysecurity.comment.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回值枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    OK(200,"操作成功!"),
    ERROR(500,"操作失败!"),
    ;

    private int code;
    private String msg;

    /**
     * 异常枚举
     */
    @Getter
    @AllArgsConstructor
    public enum ExceptionEnum{

        USER_NOT_HAVE(800,"用户不存在！"),
        NOT_LOGIN(801,"登录过期，请重新登录!"),
        NO_PERMISSION(802,"权限不足！"),
        ACCOUNT_IS_LOCK(803,"账户已被锁定！"),
        ;

        private int code;
        private String msg;
    }
}

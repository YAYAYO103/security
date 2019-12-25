package com.wangyu.mysecurity.comment.aop;

import com.wangyu.mysecurity.comment.Enums.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YAYAYO
 * @description: 记录日志
 * @date 2019.12.25 02511:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    //日志内容
    String value() default "";

    //解析日志字符串的方式（默认spel）
    Constants.LogTypeEnum type() default Constants.LogTypeEnum.SPEL;
}

package com.wangyu.mysecurity.comment.aop;

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
}

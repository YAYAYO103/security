package com.wangyu.mysecurity.comment;

/**
 * @author YAYAYO
 * @description: 自定义线程变量
 * @date 2019.12.25 02515:53
 */
public class Local{

    public static final ThreadLocal<String[]> threadLocal = new ThreadLocal<String[]>();

    public static ThreadLocal<String[]> getThreadLocal() {
        return threadLocal;
    }

    /**
     * @description: 清除当前线程信息
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    public static void clear(){
        threadLocal.remove();
    }
}

package com.wangyu.mysecurity.bean.dto;

import lombok.Data;

/**
 * @author YAYAYO
 * @description: 七牛云返回对象封装
 * @date 2019.12.20 02019:53
 */
@Data
public class DefaultPutRet {

    /**
     * //hash 七牛返回的文件存储的地址，可以使用这个地址加七牛给你提供的前缀访问到这个视频。
     */
    private String hash;

    /**
     * 文件名
     */
    private String key;
}

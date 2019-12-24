package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.utils.AliyunUtil;
import com.wangyu.mysecurity.comment.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author YAYAYO
 * @description: 图片上传工具类
 * @date 2019.12.20 02017:17
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliyunUtil aliyunUtil;

    @Autowired
    private QiNiuUtil qiNiuUtil;

    /**
     * 阿里云上传图片
     * @return
     */
    @GetMapping("/upload")
    public R upload(MultipartFile file){
        return aliyunUtil.upload(file);
    }

    /**
     * 阿里云删除图片
     * @return
     */
    @GetMapping("/deleteUpload")
    public R upload(String url){
        aliyunUtil.deleteUpload(url);
        return R.ok("删除成功！");
    }

    /**
     * 七牛云上传图片
     * @return
     */
    @GetMapping("/qupload")
    public R qupload(MultipartFile file){
        String url=qiNiuUtil.uploadMultipartFile(file);
        return R.ok("上传成功！",url);
    }

}

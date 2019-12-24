package com.wangyu.mysecurity.comment.utils;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.config.AliyunProperties;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author YAYAYO
 * @description: 阿里云工具类
 * @date 2019.12.20 02014:36
 */
@Component
public class AliyunUtil {

    @Autowired
    private AliyunProperties aliyunConfig;

    @Autowired
    private OSS oss;

    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    public R upload(MultipartFile multipartFile){
        // 1. 对上传的图片进行校验: 这里简单校验后缀名
        // 另外可通过ImageIO读取图片的长宽来判断是否是图片,校验图片的大小等。
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;  // 只要与允许上传格式其中一个匹配就可以
            }
        }

        // 格式错误, 返回与前端约定的error
        if (!isLegal) {
            return R.error("上传文件的格式错误！");
        }

        // 2. 准备上传API的参数
        String fileName = multipartFile.getOriginalFilename();
        String filePath = this.getFilePath(fileName);

        // 3. 上传至阿里OSS
        try {
            // 添加 ContentType 不设置会导致上传成功后无法预览
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(this.getContentType(multipartFile));
            oss.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(multipartFile.getBytes()),objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
            // 上传失败
            return R.error("上传文件失败！");
        }

        //拼接访问地址
        String url =aliyunConfig.getBucket()+filePath;
        // 上传成功
        return R.ok("上传文件成功！",url);
    }

    /**
     * 创建上传目录
     * @param fileName
     * @return
     */
    private String getFilePath(String fileName) {
        DateTime dateTime = new DateTime();
        return aliyunConfig.getUrlPrefix() + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(fileName, ".");
    }

    /**
     * 解决阿里云图片上传无法预览问题
     * @param
     * @return
     */
    private String getContentType(MultipartFile multipartFile) {
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), ".bmp")) {
            return "image/bmp";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".gif")) {
            return "image/gif";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".jpeg") ||
                StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".jpg") ||
                StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".png")) {
            return "image/jpg";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".html")) {
            return "text/html";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".txt")) {
            return "text/plain";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".vsd")) {
            return "application/vnd.visio";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".pptx") ||
                StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".docx") ||
                StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".doc")) {
            return "application/msword";
        }
        if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

    /**
     * 删除上传的文件
     * @param url
     * @return
     */
    public void deleteUpload(String url){
        if(StringUtils.isBlank(url)){
            throw new RRException("要删除的文件地址为空！");
        }
        //去掉域名
        url=StringUtils.replace(url,aliyunConfig.getBucket(),"");
        oss.deleteObject(aliyunConfig.getBucketName(), url);
//        ObjectListing objectListing = oss.listObjects(aliyunConfig.getBucketName());
//        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
//        for (OSSObjectSummary object : objectSummary) {
//            String picUrl=object.getKey();
//            if(org.springframework.util.StringUtils.endsWithIgnoreCase(picUrl,url)){
//                oss.deleteObject(aliyunConfig.getBucketName(), picUrl);
//                break;
//            }
//        }
    }
}

package com.wangyu.mysecurity.comment.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wangyu.mysecurity.bean.dto.DefaultPutRet;
import com.wangyu.mysecurity.comment.config.QiNiuConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author YAYAYO
 * @description: 七牛云图片上传工具类
 * @date 2019.12.20 02018:15
 */
@Component
@AllArgsConstructor
@Slf4j
public class QiNiuUtil {

    private final QiNiuConfig config;

    /**
     * 生成客户端上传所需要的上传凭证
     * @return
     */
    private Auth getAuth(){
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        return auth;
    }

    /**
     * 上传本地文件
     * @param localFilePath 本地文件完整路径
     * @param key 文件云端存储的名称
     * @param override 是否覆盖同名同位置文件
     * @return
     */
    public boolean upload(String localFilePath, String key, boolean override){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(config.getRegions());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        Auth auth =this.getAuth();
        String upToken;
        if(override){
            upToken = auth.uploadToken(config.getBucket(), key);//覆盖上传凭证
        }else{
            upToken = auth.uploadToken(config.getBucket());
        }
        try {
            //localFilePath格式是/home/qiniu/test.png（windows环境下不同）
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("文件上传失败！原因：【{}】",r.toString());
            return false;
        }
    }

    /**
     * 获取文件访问地址
     * @param fileName 文件云端存储的名称
     * @return
     * @throws UnsupportedEncodingException
     */
    public String fileUrl(String fileName) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String publicUrl = String.format("%s/%s", config.getDomainOfBucket(), encodedFileName);
        Auth auth = getAuth();
        long expireInSeconds = config.getExpireInSeconds();
        if(-1 == expireInSeconds){
            return auth.privateDownloadUrl(publicUrl);
        }
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    /**
     * 上传MultipartFile
     * @param file 上传文件
     * @return
     * @throws IOException
     */
    public String uploadMultipartFile(MultipartFile file) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(config.getRegions());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //上传文件的新名称(七牛云没有文件夹的概念)
        String newName=this.getFilePath(file.getOriginalFilename());

        //把文件转化为字节数组
        InputStream is = null;
        ByteArrayOutputStream bos = null;

        try {
            is = file.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = is.read(b)) != -1){
                bos.write(b, 0, len);
            }
            byte[] uploadBytes= bos.toByteArray();

            Auth auth = getAuth();
            String upToken;
//            if(override){
            upToken = auth.uploadToken(config.getBucket(), newName);//覆盖上传凭证
//            }else{
//                upToken = auth.uploadToken(config.getBucket());
//            }
            //默认上传接口回复对象
            DefaultPutRet putRet;
            //进行上传操作，传入文件的字节数组，文件名，上传空间，得到回复对象
            Response response = uploadManager.put(uploadBytes, newName, upToken);
            String body=response.bodyString();
            putRet = JSONUtil.toBean(body, DefaultPutRet.class);
        }catch (QiniuException e){
            log.error("文件上传失败！原因：【{}】",e.getMessage());
            return null;
        }catch (IOException e) {
            log.error("文件上传失败！原因：【{}】",e.getMessage());
            return null;
        }

        //生成外链地址
        String url=config.getDomainOfBucket()+"/"+newName;

        return url;
    }

    /**
     * 创建上传目录
     * @param fileName
     * @return
     */
    private String getFilePath(String fileName) {
        DateTime dateTime = new DateTime();
        return config.getUrlPrefix() + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(fileName, ".");
    }

}
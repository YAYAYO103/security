package com.wangyu.mysecurity.comment.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.Region;
import com.wangyu.mysecurity.comment.Exception.RRException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YAYAYO
 * @description: 七牛云参数
 * @date 2019.12.20 02018:07
 */
@Data
@ConfigurationProperties(prefix = "qiniu")
@Component
public class QiNiuConfig {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String region;

    private String domainOfBucket;

    private long expireInSeconds;

    private Region regions;

    private String urlPrefix;

    public void setRegion(String region) {
        this.region = region;
        if("region0".equals(region)){
            this.setRegions(Region.huadong());
        }else if("region1".equals(region)){
            this.setRegions(Region.huabei());
        }else if("region2".equals(region)){
            this.setRegions(Region.huanan());
        }else if("regionNa0".equals(region)){
            this.setRegions(Region.beimei());
        }else if("regionAs0".equals(region)){
            this.setRegions(Region.xinjiapo());
        }else {
            throw new RRException("七牛云【region】参数配置错误！");
        }
    }
}

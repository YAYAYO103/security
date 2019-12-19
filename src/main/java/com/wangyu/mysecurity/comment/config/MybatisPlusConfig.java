package com.wangyu.mysecurity.comment.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatisplus配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.wangyu.mysecurity.mapper")
public class MybatisPlusConfig {

    /**
     * @discription: 分页插件
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
    */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}

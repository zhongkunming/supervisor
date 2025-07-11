package com.unknown.supervisor.config;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author zhongkunming
 */
@Configuration
public class MyBatisConfig {

    /**
     * 分页插件
     */
    @Bean
    @Order
    public InnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}

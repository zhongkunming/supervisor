package com.unknown.supervisor.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhongkunming
 */
@Configuration
public class MyBatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        interceptors.add(new PaginationInnerInterceptor());
        mybatisPlusInterceptor.setInterceptors(interceptors);
        return mybatisPlusInterceptor;
    }
}

package com.unknown.supervisor.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.unknown.supervisor.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhongkunming
 */
@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final MyBatisPlusMetaObjectHandler metaObjectHandler;

    /**
     * MyBatis Plus 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 全局配置
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置字段自动填充处理器
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        // 设置ID生成器
        globalConfig.setIdentifierGenerator(identifierGenerator());
        return globalConfig;
    }

    private IdentifierGenerator identifierGenerator() {
        return new IdentifierGenerator() {
            @Override
            public Number nextId(Object entity) {
                return IdUtils.nextId();
            }

            @Override
            public String nextUUID(Object entity) {
                return String.valueOf(nextId(entity));
            }
        };
    }
}

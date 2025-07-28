package com.unknown.supervisor.config;

import com.unknown.supervisor.utils.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * 用于管理JWT相关的配置参数
 *
 * @author zhongkunming
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

    /**
     * JWT密钥
     */
    private String secret;

    /**
     * 令牌前缀
     */
    private String tokenPrefix;

    /**
     * 请求头名称
     */
    private String headerName;

    /**
     * GET请求中token参数名
     */
    private String tokenParam;

    /**
     * 初始化方法，在Spring容器初始化后自动将配置注入到JwtUtils中
     */
    @PostConstruct
    public void initJwtUtils() {
        JwtUtils.setJwtConfig(this);
    }
}
package com.unknown.supervisor.config;

import com.unknown.supervisor.utils.JwtUtils;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * JWT配置类
 * 用于管理JWT相关的配置参数
 *
 * @author zhongkunming
 * @since 1.0.0
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

    /**
     * JWT密钥
     */
    @NotBlank(message = "JWT密钥不能为空")
    private String secret;

    /**
     * 令牌前缀
     */
    @NotBlank(message = "令牌前缀不能为空")
    private String tokenPrefix;

    /**
     * 请求头名称
     */
    @NotBlank(message = "请求头名称不能为空")
    private String headerName;

    /**
     * GET请求中token参数名
     */
    @NotBlank(message = "GET请求中token参数名不能为空")
    private String tokenParam;

    /**
     * 令牌有效期，单位秒
     */
    @NotNull(message = "令牌有效期不能为空")
    @Range(min = 3600, message = "令牌有效期最小为1小时")
    private Long expire;

    /**
     * 初始化方法，在Spring容器初始化后自动将配置注入到JwtUtils中
     */
    @PostConstruct
    public void initJwtUtils() {
        JwtUtils.setJwtConfig(this);
    }
}
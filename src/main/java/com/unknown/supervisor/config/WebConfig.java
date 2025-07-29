package com.unknown.supervisor.config;

import com.unknown.supervisor.core.interceptor.SysLogInterceptor;
import com.unknown.supervisor.core.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongkunming
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SysLogInterceptor sysLogInterceptor;

    private final TokenInterceptor tokenInterceptor;

    private final List<String> publicIgnoreUri = List.of(
            "/doc.html",
            "/favicon.ico",
            "/error",
            "/swagger-ui/index.html",
            "/swagger-ui*/**",
            "/webjars/**",
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/v3/api-docs/swagger-config"
    );

    private final List<String> ignoreLogUri = new ArrayList<>() {
        {
            addAll(publicIgnoreUri);
        }
    };

    private final List<String> ignoreTokenUri = new ArrayList<>() {
        {
            addAll(publicIgnoreUri);
            add("/sys/auth/login");
        }
    };


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysLogInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignoreLogUri);

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignoreTokenUri);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}

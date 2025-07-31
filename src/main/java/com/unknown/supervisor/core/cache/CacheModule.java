package com.unknown.supervisor.core.cache;

import lombok.Getter;

/**
 * 缓存模块枚举
 * 用于标识不同业务模块的缓存，自动处理缓存key的模块前缀
 *
 * @author zhongkunming
 * @since 2024-01-01
 */
@Getter
public enum CacheModule {

    /**
     * 默认模块
     */
    DEFAULT("default", "默认模块"),

    /**
     * 令牌模块
     */
    TOKEN("token", "令牌模块");

    /**
     * 模块代码
     */
    private final String code;

    /**
     * 模块描述
     */
    private final String description;

    CacheModule(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据代码获取模块
     *
     * @param code 模块代码
     * @return 缓存模块
     */
    public static CacheModule fromCode(String code) {
        for (CacheModule module : values()) {
            if (module.code.equals(code)) {
                return module;
            }
        }
        throw new IllegalArgumentException("未知的缓存模块代码: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
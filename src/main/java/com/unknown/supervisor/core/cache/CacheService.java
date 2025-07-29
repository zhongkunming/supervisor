package com.unknown.supervisor.core.cache;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 通用缓存操作接口
 *
 * @author zhongkunming
 * @since 2024-01-01
 */
public interface CacheService {

    /**
     * 读取缓存（带模块）
     *
     * @param module 缓存模块
     * @param key    缓存键
     * @param <T>    值类型
     * @return 缓存值
     */
    <T> T get(CacheModule module, String key);

    /**
     * 读取缓存，如果缓存为空则调用生产者方法并存入缓存（带模块）
     *
     * @param module   缓存模块
     * @param key      缓存键
     * @param supplier 生产者方法
     * @param <T>      值类型
     * @return 缓存值
     */
    <T> T get(CacheModule module, String key, Supplier<T> supplier);

    /**
     * 读取缓存，如果缓存为空则调用生产者方法并存入缓存（带模块和过期时间）
     *
     * @param module   缓存模块
     * @param key      缓存键
     * @param supplier 生产者方法
     * @param duration 过期时间
     * @param <T>      值类型
     * @return 缓存值
     */
    <T> T get(CacheModule module, String key, Supplier<T> supplier, Duration duration);

    /**
     * 写入缓存（带模块）
     *
     * @param module 缓存模块
     * @param key    缓存键
     * @param value  缓存值
     */
    void put(CacheModule module, String key, Object value);

    /**
     * 写入缓存（带模块和过期时间）
     *
     * @param module   缓存模块
     * @param key      缓存键
     * @param value    缓存值
     * @param duration 过期时间
     */
    void put(CacheModule module, String key, Object value, Duration duration);

    /**
     * 删除缓存（带模块）
     *
     * @param module 缓存模块
     * @param key    缓存键
     * @return 是否删除成功
     */
    boolean delete(CacheModule module, String key);


    /**
     * 检查缓存是否存在（带模块）
     *
     * @param module 缓存模块
     * @param key    缓存键
     * @return 是否存在
     */
    boolean exists(CacheModule module, String key);

    /**
     * 设置缓存过期时间（带模块）
     *
     * @param module   缓存模块
     * @param key      缓存键
     * @param duration 过期时间
     * @return 是否设置成功
     */
    boolean expire(CacheModule module, String key, Duration duration);

    /**
     * 获取模块下所有匹配模式的键
     *
     * @param module  缓存模块
     * @param pattern 匹配模式（不包含模块前缀）
     * @return 键集合（不包含模块前缀）
     */
    Set<String> keys(CacheModule module, String pattern);

    /**
     * 清空模块下所有缓存
     *
     * @param module 缓存模块
     */
    void clear(CacheModule module);
}
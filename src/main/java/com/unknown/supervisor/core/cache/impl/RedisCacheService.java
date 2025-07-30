package com.unknown.supervisor.core.cache.impl;

import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Redis缓存服务实现
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheService implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public <T> T get(CacheModule module, String key) {
        return get(module, key, null, null);
    }

    @Override
    public <T> T get(CacheModule module, String key, Supplier<T> supplier) {
        return get(module, key, supplier, null);
    }

    @Override
    public <T> T get(CacheModule module, String key, Supplier<T> supplier, Duration duration) {
        if (StringUtils.isBlank(key)) return null;
        String realKey = buildRealKey(module, key);

        try {
            T t = (T) redisTemplate.opsForValue().get(realKey);
            if (t != null) {
                return t;
            }
            if (supplier != null) {
                t = supplier.get();
            }
            if (t != null) {
                put(module, key, t, duration);
            }
            return t;
        } catch (Exception e) {
            log.error("获取缓存失败，key: {}", key, e);
            return null;
        }
    }

    @Override
    public void put(CacheModule module, String key, Object value) {
        put(module, key, value, null);
    }

    @Override
    public void put(CacheModule module, String key, Object value, Duration duration) {
        if (StringUtils.isBlank(key)) return;
        String realKey = buildRealKey(module, key);

        try {
            if (value == null) {
                delete(module, key);
            } else if (duration == null) {
                redisTemplate.opsForValue().set(realKey, value);
            } else {
                redisTemplate.opsForValue().set(realKey, value, duration);
            }
        } catch (Exception e) {
            log.error("设置缓存失败，key: {}, value: {}, duration: {}", key, value, duration, e);
        }
    }

    @Override
    public boolean delete(CacheModule module, String key) {
        if (StringUtils.isBlank(key)) return false;
        String realKey = buildRealKey(module, key);

        try {
            return redisTemplate.delete(realKey);
        } catch (Exception e) {
            log.error("删除缓存失败，key: {}", key, e);
            return false;
        }
    }

    @Override
    public boolean exists(CacheModule module, String key) {
        if (StringUtils.isBlank(key)) return false;
        String realKey = buildRealKey(module, key);

        try {
            return redisTemplate.hasKey(realKey);
        } catch (Exception e) {
            log.error("判断缓存存在失败，key: {}", key, e);
            return false;
        }
    }

    @Override
    public boolean expire(CacheModule module, String key, Duration duration) {
        if (StringUtils.isBlank(key)) return false;
        String realKey = buildRealKey(module, key);

        try {
            return Boolean.TRUE.equals(redisTemplate.expire(realKey, duration));
        } catch (Exception e) {
            log.error("更新国企过期时间缓存失败，key: {}", key, e);
            return false;
        }
    }

    @Override
    public Set<String> keys(CacheModule module, String pattern) {
        if (StringUtils.isBlank(pattern)) return Collections.emptySet();
        String realPattern = buildRealKey(module, pattern);

        Set<String> keys = new HashSet<>();
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions().match(realPattern).build())) {
            while (cursor.hasNext()) keys.add(cursor.next());
            return keys;
        } catch (Exception e) {
            log.error("获取匹配键失败，pattern: {}", pattern, e);
            return Collections.emptySet();
        }
    }

    @Override
    public void clear(CacheModule module) {
        try {
            Set<String> keys = keys(module, "*");
            redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("清空缓存失败", e);
        }
    }

    private String buildRealKey(CacheModule module, String key) {
        if (module == null) module = CacheModule.DEFAULT;
        String realKey = appName + ":" + module.getCode();
        if (StringUtils.isBlank(key)) return realKey;
        return realKey + ":" + key;
    }
}
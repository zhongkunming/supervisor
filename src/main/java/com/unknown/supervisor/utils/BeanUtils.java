package com.unknown.supervisor.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Bean工具类
 * 封装Spring BeanUtils，便于后续切换不同的实现方式
 *
 * @author zhongkunming
 */
public final class BeanUtils {

    private BeanUtils() {
        // 工具类不允许实例化
    }

    /**
     * 拷贝对象属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(@NonNull Object source, @NonNull Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 拷贝对象属性，忽略指定属性
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 忽略的属性名
     */
    public static void copyProperties(@NonNull Object source, @NonNull Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 拷贝对象属性到新对象
     *
     * @param source         源对象
     * @param targetSupplier 目标对象供应商
     * @param <T>            目标对象类型
     * @return 拷贝后的新对象
     */
    public static <T> T copyProperties(@Nullable Object source, @NonNull Supplier<T> targetSupplier) {
        if (source == null) {
            return null;
        }
        T target = targetSupplier.get();
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 拷贝对象属性到新对象，忽略指定属性
     *
     * @param source           源对象
     * @param targetSupplier   目标对象供应商
     * @param ignoreProperties 忽略的属性名
     * @param <T>              目标对象类型
     * @return 拷贝后的新对象
     */
    public static <T> T copyProperties(@Nullable Object source, @NonNull Supplier<T> targetSupplier, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        T target = targetSupplier.get();
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * 拷贝列表对象属性
     *
     * @param sourceList     源对象列表
     * @param targetSupplier 目标对象供应商
     * @param <T>            目标对象类型
     * @return 拷贝后的新对象列表
     */
    public static <T> List<T> copyPropertiesList(@Nullable List<?> sourceList, @NonNull Supplier<T> targetSupplier) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            if (source != null) {
                T target = targetSupplier.get();
                org.springframework.beans.BeanUtils.copyProperties(source, target);
                targetList.add(target);
            }
        }
        return targetList;
    }

    /**
     * 拷贝列表对象属性，忽略指定属性
     *
     * @param sourceList       源对象列表
     * @param targetSupplier   目标对象供应商
     * @param ignoreProperties 忽略的属性名
     * @param <T>              目标对象类型
     * @return 拷贝后的新对象列表
     */
    public static <T> List<T> copyPropertiesList(@Nullable List<?> sourceList, @NonNull Supplier<T> targetSupplier, String... ignoreProperties) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            if (source != null) {
                T target = targetSupplier.get();
                org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
                targetList.add(target);
            }
        }
        return targetList;
    }
}
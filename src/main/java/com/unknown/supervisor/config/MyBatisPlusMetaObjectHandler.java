package com.unknown.supervisor.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.unknown.supervisor.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 字段自动填充处理器
 *
 * @author zhongkunming
 */
@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String operNo = getCurrentOperNo();
        LocalDateTime now = LocalDateTime.now();

        this.strictInsertFill(metaObject, "createOperNo", String.class, operNo);
        this.strictInsertFill(metaObject, "createDt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateOperNo", String.class, operNo);
        this.strictInsertFill(metaObject, "updateDt", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String operNo = getCurrentOperNo();
        LocalDateTime now = LocalDateTime.now();

        this.strictUpdateFill(metaObject, "updateOperNo", String.class, operNo);
        this.strictUpdateFill(metaObject, "updateDt", LocalDateTime.class, now);
    }

    /**
     * 获取当前操作员编号
     */
    private String getCurrentOperNo() {
        try {
            return JwtUtils.getOperNo();
        } catch (Exception e) {
            log.error("获取当前操作员号失败，使用默认值。失败原因: {}", e.getMessage());
        }
        return "system";
    }
}
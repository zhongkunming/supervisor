package com.unknown.supervisor.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.unknown.supervisor.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

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
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createDt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateDt", LocalDateTime.class, now);

        if (Objects.isNull(this.getFieldValByName("createOperNo", metaObject)) ||
                Objects.isNull(this.getFieldValByName("updateOperNo", metaObject))) {
            String operNo = getCurrentOperNo();
            this.strictInsertFill(metaObject, "createOperNo", String.class, operNo);
            this.strictInsertFill(metaObject, "updateOperNo", String.class, operNo);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateDt", LocalDateTime.class, now);

        if (Objects.isNull(this.getFieldValByName("updateOperNo", metaObject))) {
            String operNo = getCurrentOperNo();
            this.strictInsertFill(metaObject, "updateOperNo", String.class, operNo);
        }
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
        return "anno";
    }
}
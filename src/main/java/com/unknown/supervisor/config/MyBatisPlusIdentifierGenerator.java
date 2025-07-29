package com.unknown.supervisor.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.unknown.supervisor.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhongkunming
 */
@Configuration
@RequiredArgsConstructor
public class MyBatisPlusIdentifierGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return IdUtils.nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return String.valueOf(nextId(entity));
    }
}

package com.unknown.supervisor.portal.service.impl;

import com.unknown.supervisor.portal.entity.SysLog;
import com.unknown.supervisor.portal.mapper.SysLogMapper;
import com.unknown.supervisor.portal.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {

    private final SysLogMapper sysLogMapper;

    @Override
    @Async
    public void saveAsync(SysLog sysLog) {
        try {
            sysLogMapper.insert(sysLog);
        } catch (Exception e) {
            log.error("保存请求日志失败", e);
        }
    }
}
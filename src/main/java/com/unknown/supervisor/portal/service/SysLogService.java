package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.entity.SysLog;

/**
 * 系统日志Service接口
 *
 * @author zhongkunming
 */
public interface SysLogService {

    /**
     * 保存系统日志
     *
     * @param sysLog 系统日志
     */
    void saveLog(SysLog sysLog);
}
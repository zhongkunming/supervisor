package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.entity.SysLog;

/**
 * 系统日志服务接口
 *
 * @author zhongkunming
 */
public interface SysLogService {

    /**
     * 异步保存请求日志
     *
     * @param sysLog 请求日志
     */
    void saveAsync(SysLog sysLog);
}
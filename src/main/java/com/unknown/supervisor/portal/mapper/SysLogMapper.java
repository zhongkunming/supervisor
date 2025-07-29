package com.unknown.supervisor.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.portal.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志Mapper接口
 *
 * @author zhongkunming
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
package com.unknown.supervisor.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.portal.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联Mapper接口
 *
 * @author zhongkunming
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
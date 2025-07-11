package com.unknown.supervisor.web.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.web.portal.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhongkunming
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser selectByUsername(String username);
}
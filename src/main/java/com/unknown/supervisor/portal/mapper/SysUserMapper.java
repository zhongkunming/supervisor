package com.unknown.supervisor.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.portal.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统用户Mapper接口
 *
 * @author zhongkunming
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据操作员编号查询用户
     *
     * @param operNo 操作员编号
     * @return 用户信息
     */
    @Select("select * from sys_user where oper_no = #{operNo}")
    SysUser selectByOperNo(@Param("operNo") String operNo);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Select("select * from sys_user where username = #{username}")
    SysUser selectByUsername(@Param("username") String username);
}
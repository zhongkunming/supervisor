package com.unknown.supervisor.portal.mapper;

import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhongkunming
 */
@Mapper
public interface SysPermMapper {

    List<SysRole> selectRolesByOperNo(@Param("operNo") String operNo);

    List<String> selectMenuPermsByOperNo(@Param("operNo") String operNo);

    List<SysMenu> selectMenusByOperNo(@Param("operNo") String operNo);
}

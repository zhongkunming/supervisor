package com.unknown.supervisor.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.portal.dto.role.SysUserRoleQueryDTO;
import com.unknown.supervisor.portal.entity.SysUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色关联Mapper
 *
 * @author zhongkunming
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据操作员编号删除用户角色关联
     *
     * @param operNo 操作员编号
     */
    @Delete("delete from sys_user_role where oper_no = #{operNo}")
    void deleteByOperNo(@Param("operNo") String operNo);

    /**
     * 查询用户角色列表
     *
     * @param operNo 操作员编号
     * @return 用户角色列表
     */
    @Select("select sur.role_code, sr.role_name, sr.role_desc, sr.status " +
            "from sys_user_role sur " +
            "left join sys_role sr on sur.role_code = sr.role_code " +
            "where sur.oper_no = #{operNo} " +
            "order by sr.sort_order ")
    List<SysUserRoleQueryDTO> selectUserRoleList(@Param("operNo") String operNo);

    /**
     * 根据操作员编号查询角色编码列表
     *
     * @param operNo 操作员编号
     * @return 角色编码列表
     */
    @Select("select role_code from sys_user_role where oper_no = #{operNo}")
    List<String> selectRoleCodesByOperNo(@Param("operNo") String operNo);
}
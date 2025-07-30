package com.unknown.supervisor.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.supervisor.portal.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色菜单关联Mapper
 *
 * @author zhongkunming
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色编码查询菜单编码列表
     *
     * @param roleCode 角色编码
     * @return 菜单编码列表
     */
    @Select("select menu_code from sys_role_menu where role_code = #{roleCode}")
    List<String> selectMenuCodesByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 根据角色编码删除关联关系
     *
     * @param roleCode 角色编码
     */
    @Delete("delete from sys_role_menu where role_code = #{roleCode}")
    void deleteByRoleCode(@Param("roleCode") String roleCode);
}
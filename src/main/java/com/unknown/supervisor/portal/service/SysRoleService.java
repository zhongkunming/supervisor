package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.role.*;

/**
 * 角色信息Service接口
 *
 * @author zhongkunming
 */
public interface SysRoleService {

    /**
     * 分页查询角色列表
     *
     * @param inputVO 查询条件
     * @return 角色列表
     */
    PageResult<SysRoleVO> pageQuery(SysRoleQueryInputVO inputVO);

    /**
     * 根据ID查询角色信息
     *
     * @param inputVO 查询角色
     * @return 角色信息
     */
    SysRoleVO getById(SysRoleGetInputVO inputVO);

    /**
     * 新增角色
     *
     * @param inputVO 角色信息
     */
    void createRole(SysRoleCreateInputVO inputVO);

    /**
     * 修改角色
     *
     * @param inputVO 角色信息
     */
    void updateRole(SysRoleUpdateInputVO inputVO);

    /**
     * 删除角色
     *
     * @param inputVO 角色ID列表
     */
    void deleteRole(SysRoleDeleteInputVO inputVO);
}
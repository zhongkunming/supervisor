package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.userrole.*;

/**
 * 用户角色关联Service接口
 *
 * @author zhongkunming
 */
public interface SysUserRoleService {

    /**
     * 分页查询用户角色关联列表
     *
     * @param inputVO 查询条件
     * @return 用户角色关联列表
     */
    PageResult<SysUserRoleVO> pageQuery(SysUserRoleQueryInputVO inputVO);

    /**
     * 根据ID查询用户角色关联信息
     *
     * @param inputVO 查询用户角色关联
     * @return 用户角色关联信息
     */
    SysUserRoleVO getById(SysUserRoleGetInputVO inputVO);

    /**
     * 新增用户角色关联
     *
     * @param inputVO 用户角色关联信息
     */
    void createUserRole(SysUserRoleCreateInputVO inputVO);

    /**
     * 修改用户角色关联
     *
     * @param inputVO 用户角色关联信息
     */
    void updateUserRole(SysUserRoleUpdateInputVO inputVO);

    /**
     * 删除用户角色关联
     *
     * @param inputVO 用户角色关联ID列表
     */
    void deleteUserRole(SysUserRoleDeleteInputVO inputVO);
}
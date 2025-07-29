package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.user.SysUserCreateInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.dto.user.SysUserQueryInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserUpdateInputDTO;

public interface SysUserService {

    // ========== Controller层调用的公共方法 ==========

    /**
     * 分页查询用户
     */
    PageResult<SysUserDTO> pageUsers(SysUserQueryInputDTO pageDTO);

    /**
     * 根据ID查询用户
     */
    SysUserDTO getUserById(Long id);

    /**
     * 创建用户
     */
    void createUser(SysUserCreateInputDTO createDTO);

    /**
     * 更新用户
     */
    void updateUser(SysUserUpdateInputDTO updateDTO);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 启用/禁用用户
     */
    void updateUserStatus(Long id, Integer status);

    // ========== 内部使用的方法 ==========

    /**
     * 根据操作员编号查询用户
     */
    SysUserDTO getUserByOperNo(String operNo);

    /**
     * 根据用户名查询用户
     */
    SysUserDTO getUserByUsername(String username);
}
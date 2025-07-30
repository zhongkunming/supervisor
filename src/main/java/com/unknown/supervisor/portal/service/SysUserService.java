package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.vo.user.SysUserCreateInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserQueryInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserUpdateInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserVO;

public interface SysUserService {

    // ========== Controller层调用的公共方法 ==========

    /**
     * 分页查询用户
     */
    PageResult<SysUserVO> pageUsers(SysUserQueryInputVO queryInputVO);

    /**
     * 根据ID查询用户
     */
    SysUserVO getUserById(Long id);

    /**
     * 创建用户
     */
    void createUser(SysUserCreateInputVO createInputVO);

    /**
     * 更新用户
     */
    void updateUser(SysUserUpdateInputVO updateInputVO);

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
package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.entity.SysUser;
import com.unknown.supervisor.portal.vo.user.*;

/**
 * 用户信息Service接口
 *
 * @author zhongkunming
 */
public interface SysUserService {

    /**
     * 分页查询用户列表
     *
     * @param inputVO 查询条件
     * @return 用户列表
     */
    PageResult<SysUserVO> pageQuery(SysUserQueryInputVO inputVO);

    /**
     * 根据ID查询用户信息
     *
     * @param inputVO 查询用户
     * @return 用户信息
     */
    SysUserVO getById(SysUserGetInputVO inputVO);

    /**
     * 新增用户
     *
     * @param inputVO 用户信息
     */
    void createUser(SysUserCreateInputVO inputVO);

    /**
     * 修改用户
     *
     * @param inputVO 用户信息
     */
    void updateUser(SysUserUpdateInputVO inputVO);

    /**
     * 删除用户
     *
     * @param inputVO 用户ID列表
     */
    void deleteUser(SysUserDeleteInputVO inputVO);


    /**
     * 根据操作员号查询用户信息
     *
     * @param operNo 操作员号
     * @return 用户信息
     */
    SysUser getUserByOperNo(String operNo);
}
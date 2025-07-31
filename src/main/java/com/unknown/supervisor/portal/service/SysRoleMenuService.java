package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.rolemenu.*;

/**
 * 角色菜单关联Service接口
 *
 * @author zhongkunming
 */
public interface SysRoleMenuService {

    /**
     * 分页查询角色菜单关联列表
     *
     * @param inputVO 查询条件
     * @return 角色菜单关联列表
     */
    PageResult<SysRoleMenuVO> pageQuery(SysRoleMenuQueryInputVO inputVO);

    /**
     * 根据ID查询角色菜单关联信息
     *
     * @param inputVO 查询角色菜单关联
     * @return 角色菜单关联信息
     */
    SysRoleMenuVO getById(SysRoleMenuGetInputVO inputVO);

    /**
     * 新增角色菜单关联
     *
     * @param inputVO 角色菜单关联信息
     */
    void createRoleMenu(SysRoleMenuCreateInputVO inputVO);

    /**
     * 修改角色菜单关联
     *
     * @param inputVO 角色菜单关联信息
     */
    void updateRoleMenu(SysRoleMenuUpdateInputVO inputVO);

    /**
     * 删除角色菜单关联
     *
     * @param inputVO 角色菜单关联ID列表
     */
    void deleteRoleMenu(SysRoleMenuDeleteInputVO inputVO);
}
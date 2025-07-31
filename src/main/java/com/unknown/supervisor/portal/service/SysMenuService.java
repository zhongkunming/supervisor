package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.menu.*;

/**
 * 菜单权限Service接口
 *
 * @author zhongkunming
 */
public interface SysMenuService {

    /**
     * 分页查询菜单列表
     *
     * @param inputVO 查询条件
     * @return 菜单列表
     */
    PageResult<SysMenuVO> pageQuery(SysMenuQueryInputVO inputVO);

    /**
     * 根据ID查询菜单信息
     *
     * @param inputVO 查询菜单
     * @return 菜单信息
     */
    SysMenuVO getById(SysMenuGetInputVO inputVO);

    /**
     * 新增菜单
     *
     * @param inputVO 菜单信息
     */
    void createMenu(SysMenuCreateInputVO inputVO);

    /**
     * 修改菜单
     *
     * @param inputVO 菜单信息
     */
    void updateMenu(SysMenuUpdateInputVO inputVO);

    /**
     * 删除菜单
     *
     * @param inputVO 菜单ID列表
     */
    void deleteMenu(SysMenuDeleteInputVO inputVO);
}
package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.role.SysRoleDTO;
import com.unknown.supervisor.portal.vo.role.*;

import java.util.List;

/**
 * 系统角色服务接口
 *
 * @author zhongkunming
 */
public interface SysRoleService {

    // ========== Controller层调用的公共方法 ==========

    /**
     * 分页查询角色
     *
     * @param queryInputVO 查询条件
     * @return 分页结果
     */
    PageResult<SysRoleVO> pageRoles(SysRoleQueryInputVO queryInputVO);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    SysRoleVO getRoleById(Long id);

    /**
     * 创建角色
     *
     * @param createInputVO 创建参数
     */
    void createRole(SysRoleCreateInputVO createInputVO);

    /**
     * 更新角色
     *
     * @param updateInputVO 更新参数
     */
    void updateRole(SysRoleUpdateInputVO updateInputVO);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteRole(Long id);

    /**
     * 分配角色权限
     *
     * @param assignInputVO 分配参数
     */
    void assignRoleMenus(SysRoleMenuAssignInputVO assignInputVO);

    /**
     * 查询角色拥有的菜单权限
     *
     * @param queryInputVO 角色菜单查询输入VO
     * @return 菜单编码列表
     */
    List<String> getRoleMenus(SysRoleMenuQueryInputVO queryInputVO);

    /**
     * 查询所有启用的角色
     *
     * @return 角色列表
     */
    List<SysRoleVO> listEnabledRoles();

    // ========== Service层内部调用的方法 ==========

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色信息
     */
    SysRoleDTO getRoleByCode(String roleCode);

    /**
     * 检查角色编码是否存在
     *
     * @param roleCode  角色编码
     * @param excludeId 排除的ID
     * @return 是否存在
     */
    boolean existsByRoleCode(String roleCode, Long excludeId);
}
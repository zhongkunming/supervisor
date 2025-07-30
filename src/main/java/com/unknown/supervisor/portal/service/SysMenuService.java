package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.menu.SysMenuDTO;
import com.unknown.supervisor.portal.vo.menu.*;

import java.util.List;

/**
 * 系统菜单服务接口
 *
 * @author zhongkunming
 */
public interface SysMenuService {

    // ========== Controller层调用的公共方法 ==========

    /**
     * 分页查询菜单
     *
     * @param queryInputVO 查询条件
     * @return 分页结果
     */
    PageResult<SysMenuVO> pageMenus(SysMenuQueryInputVO queryInputVO);

    /**
     * 查询菜单树形结构
     *
     * @return 菜单树
     */
    List<SysMenuTreeOutputVO> getMenuTree();

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    SysMenuVO getMenuById(Long id);

    /**
     * 创建菜单
     *
     * @param createInputVO 创建参数
     */
    void createMenu(SysMenuCreateInputVO createInputVO);

    /**
     * 更新菜单
     *
     * @param updateInputVO 更新参数
     */
    void updateMenu(SysMenuUpdateInputVO updateInputVO);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    void deleteMenu(Long id);

    /**
     * 查询所有启用的菜单
     *
     * @return 菜单列表
     */
    List<SysMenuVO> listEnabledMenus();

    // ========== Service层内部调用的方法 ==========

    /**
     * 根据菜单编码查询菜单
     *
     * @param menuCode 菜单编码
     * @return 菜单信息
     */
    SysMenuDTO getMenuByCode(String menuCode);

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode  菜单编码
     * @param excludeId 排除的ID
     * @return 是否存在
     */
    boolean existsByMenuCode(String menuCode, Long excludeId);

    /**
     * 检查是否有子菜单
     *
     * @param parentId 父菜单ID
     * @return 是否有子菜单
     */
    boolean hasChildren(Long parentId);

    /**
     * 构建菜单树形结构
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menuList);
}
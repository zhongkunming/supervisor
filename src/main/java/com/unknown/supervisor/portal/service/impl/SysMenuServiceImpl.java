package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.menu.SysMenuDTO;
import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.mapper.SysMenuMapper;
import com.unknown.supervisor.portal.service.SysMenuService;
import com.unknown.supervisor.portal.vo.menu.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现类
 *
 * @author zhongkunming
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public PageResult<SysMenuVO> pageMenus(SysMenuQueryInputVO queryInputVO) {
        Page<SysMenu> page = queryInputVO.toPage();

        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(queryInputVO.getMenuCode()), SysMenu::getMenuCode, queryInputVO.getMenuCode())
                .like(StringUtils.isNotBlank(queryInputVO.getMenuName()), SysMenu::getMenuName, queryInputVO.getMenuName())
                .eq(queryInputVO.getPid() != null, SysMenu::getPid, queryInputVO.getPid())
                .eq(queryInputVO.getMenuType() != null, SysMenu::getMenuType, queryInputVO.getMenuType())
                .eq(queryInputVO.getStatus() != null, SysMenu::getStatus, queryInputVO.getStatus())
                .eq(queryInputVO.getVisible() != null, SysMenu::getVisible, queryInputVO.getVisible())
                .orderByAsc(SysMenu::getSortOrder);

        sysMenuMapper.selectPage(page, wrapper);

        // 转换为VO
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public List<SysMenuTreeOutputVO> getMenuTree() {
        List<SysMenu> allMenus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getStatus, 1)
                        .orderByAsc(SysMenu::getSortOrder)
        );

        List<SysMenuDTO> menuDTOList = allMenus.stream()
                .map(menu -> BeanUtils.copyProperties(menu, SysMenuDTO::new))
                .toList();

        List<SysMenuDTO> treeDTO = buildMenuTree(menuDTOList);

        // 转换为TreeOutputVO
        return treeDTO.stream()
                .map(this::convertToTreeVO)
                .toList();
    }

    @Override
    public SysMenuVO getMenuById(Long id) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }
        return this.convertToVO(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenuCreateInputVO createInputVO) {
        // 检查菜单编码是否已存在
        if (existsByMenuCode(createInputVO.getMenuCode(), null)) {
            throw new BusinessException(PortalResultCode.MENU_CODE_EXISTS);
        }

        SysMenu menu = BeanUtils.copyProperties(createInputVO, SysMenu::new);
        sysMenuMapper.insert(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuUpdateInputVO updateInputVO) {
        SysMenu existingMenu = sysMenuMapper.selectById(updateInputVO.getId());
        if (existingMenu == null) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }

        // 检查菜单编码是否已被其他菜单使用
        if (existsByMenuCode(updateInputVO.getMenuCode(), updateInputVO.getId())) {
            throw new BusinessException(PortalResultCode.MENU_CODE_EXISTS);
        }

        SysMenu menu = BeanUtils.copyProperties(updateInputVO, SysMenu::new);
        sysMenuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long id) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }

        // 检查是否有子菜单
        if (hasChildren(id)) {
            throw new BusinessException(PortalResultCode.MENU_HAS_CHILDREN);
        }

        sysMenuMapper.deleteById(id);
    }

    @Override
    public List<SysMenuVO> listEnabledMenus() {
        List<SysMenu> menus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getStatus, 1)
                        .orderByAsc(SysMenu::getSortOrder)
        );

        return menus.stream()
                .map(menu -> BeanUtils.copyProperties(menu, SysMenuVO::new))
                .toList();
    }

    // ========== 服务层内部调用方法 ==========

    @Override
    public SysMenuDTO getMenuByCode(String menuCode) {
        SysMenu menu = sysMenuMapper.selectOne(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getMenuCode, menuCode)
                        .last("LIMIT 1")
        );
        if (menu == null) {
            return null;
        }
        return this.convertToDTO(menu);
    }

    @Override
    public boolean existsByMenuCode(String menuCode, Long excludeId) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getMenuCode, menuCode)
                .ne(excludeId != null, SysMenu::getId, excludeId);
        return sysMenuMapper.exists(wrapper);
    }

    @Override
    public boolean hasChildren(Long parentId) {
        return sysMenuMapper.exists(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getPid, parentId)
        );
    }

    @Override
    public List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menuList) {
        return buildMenuTreeRecursive(menuList, 0L);
    }

    private List<SysMenuDTO> buildMenuTreeRecursive(List<SysMenuDTO> menuList, Long parentId) {
        List<SysMenuDTO> result = new ArrayList<>();

        // 按父ID分组
        Map<Long, List<SysMenuDTO>> menuMap = menuList.stream()
                .collect(Collectors.groupingBy(SysMenuDTO::getPid));

        // 获取指定父ID的菜单
        List<SysMenuDTO> parentMenus = menuMap.get(parentId);
        if (parentMenus == null) {
            return result;
        }

        for (SysMenuDTO menu : parentMenus) {
            // 递归构建子菜单
            List<SysMenuDTO> children = buildMenuTreeRecursive(menuList, menu.getId());
            menu.setChildren(children);
            result.add(menu);
        }

        return result;
    }

    /**
     * 递归转换菜单DTO为树形VO
     */
    private SysMenuTreeOutputVO convertToTreeVO(SysMenuDTO dto) {
        SysMenuTreeOutputVO vo = BeanUtils.copyProperties(dto, SysMenuTreeOutputVO::new);
        if (dto.getChildren() != null && !dto.getChildren().isEmpty()) {
            List<SysMenuTreeOutputVO> childrenVO = dto.getChildren().stream()
                    .map(this::convertToTreeVO)
                    .toList();
            vo.setChildren(childrenVO);
        }
        return vo;
    }

    private SysMenuDTO convertToDTO(SysMenu entity) {
        return BeanUtils.copyProperties(entity, SysMenuDTO::new);
    }

    private SysMenuVO convertToVO(SysMenu entity) {
        return BeanUtils.copyProperties(entity, SysMenuVO::new);
    }
}
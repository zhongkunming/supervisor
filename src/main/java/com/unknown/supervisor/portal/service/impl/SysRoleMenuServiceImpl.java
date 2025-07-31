package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.rolemenu.SysRoleMenuDTO;
import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.entity.SysRole;
import com.unknown.supervisor.portal.entity.SysRoleMenu;
import com.unknown.supervisor.portal.mapper.SysMenuMapper;
import com.unknown.supervisor.portal.mapper.SysRoleMapper;
import com.unknown.supervisor.portal.mapper.SysRoleMenuMapper;
import com.unknown.supervisor.portal.service.SysRoleMenuService;
import com.unknown.supervisor.portal.vo.rolemenu.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 角色菜单关联Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public PageResult<SysRoleMenuVO> pageQuery(SysRoleMenuQueryInputVO inputVO) {
        Page<SysRoleMenu> page = inputVO.toPage();
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysRoleMenu::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getRoleCode()), SysRoleMenu::getRoleCode, inputVO.getRoleCode())
                .like(StringUtils.isNotBlank(inputVO.getMenuCode()), SysRoleMenu::getMenuCode, inputVO.getMenuCode())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysRoleMenu::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysRoleMenu::getUpdateDt, inputVO.getUpdateDt())
                .orderByDesc(SysRoleMenu::getCreateDt);

        sysRoleMenuMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysRoleMenuVO getById(SysRoleMenuGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysRoleMenu roleMenu = sysRoleMenuMapper.selectById(id);
        if (Objects.isNull(roleMenu)) {
            throw new BusinessException(PortalResultCode.ROLE_MENU_NOT_FOUND);
        }
        return convertToVO(roleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRoleMenu(SysRoleMenuCreateInputVO inputVO) {
        String roleCode = inputVO.getRoleCode();
        String menuCode = inputVO.getMenuCode();

        // 检查角色是否存在
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRole::getCode, roleCode)
                .eq(SysRole::getIsDelete, false);
        if (!sysRoleMapper.exists(roleWrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查菜单是否存在
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.eq(SysMenu::getCode, menuCode);
        if (!sysMenuMapper.exists(menuWrapper)) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }

        // 检查角色菜单关联是否已存在
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleCode, roleCode)
                .eq(SysRoleMenu::getMenuCode, menuCode);
        if (sysRoleMenuMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_MENU_ALREADY_EXISTS);
        }

        SysRoleMenu sysRoleMenu = BeanUtils.copyProperties(inputVO, SysRoleMenu::new);
        sysRoleMenuMapper.insert(sysRoleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenu(SysRoleMenuUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysRoleMenu existRoleMenu = sysRoleMenuMapper.selectById(id);
        if (Objects.isNull(existRoleMenu)) {
            throw new BusinessException(PortalResultCode.ROLE_MENU_NOT_FOUND);
        }

        String roleCode = inputVO.getRoleCode();
        String menuCode = inputVO.getMenuCode();

        // 检查角色是否存在
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRole::getCode, roleCode)
                .eq(SysRole::getIsDelete, false);
        if (!sysRoleMapper.exists(roleWrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查菜单是否存在
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.eq(SysMenu::getCode, menuCode);
        if (!sysMenuMapper.exists(menuWrapper)) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }

        // 检查角色菜单关联是否被其他记录使用
        if (!Objects.equals(roleCode, existRoleMenu.getRoleCode()) || !Objects.equals(menuCode, existRoleMenu.getMenuCode())) {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getRoleCode, roleCode)
                    .eq(SysRoleMenu::getMenuCode, menuCode)
                    .ne(SysRoleMenu::getId, id);
            if (sysRoleMenuMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.ROLE_MENU_ALREADY_EXISTS);
            }
        }

        SysRoleMenu sysRoleMenu = BeanUtils.copyProperties(inputVO, SysRoleMenu::new);
        sysRoleMenuMapper.updateById(sysRoleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenu(SysRoleMenuDeleteInputVO inputVO) {
        for (Long id : inputVO.getIds()) {
            SysRoleMenu existRoleMenu = sysRoleMenuMapper.selectById(id);
            if (Objects.isNull(existRoleMenu)) {
                throw new BusinessException(PortalResultCode.ROLE_MENU_NOT_FOUND);
            }
        }

        // 物理删除
        sysRoleMenuMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysRoleMenuVO convertToVO(SysRoleMenu roleMenu) {
        if (Objects.isNull(roleMenu)) return null;
        return BeanUtils.copyProperties(roleMenu, SysRoleMenuVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysRoleMenuDTO convertToDTO(SysRoleMenu roleMenu) {
        if (Objects.isNull(roleMenu)) return null;
        return BeanUtils.copyProperties(roleMenu, SysRoleMenuDTO::new);
    }
}
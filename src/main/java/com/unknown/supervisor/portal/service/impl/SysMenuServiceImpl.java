package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.menu.SysMenuDTO;
import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.entity.SysRoleMenu;
import com.unknown.supervisor.portal.mapper.SysMenuMapper;
import com.unknown.supervisor.portal.mapper.SysRoleMenuMapper;
import com.unknown.supervisor.portal.service.SysMenuService;
import com.unknown.supervisor.portal.vo.menu.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 菜单权限Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public PageResult<SysMenuVO> pageQuery(SysMenuQueryInputVO inputVO) {
        Page<SysMenu> page = inputVO.toPage();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysMenu::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getCode()), SysMenu::getCode, inputVO.getCode())
                .like(StringUtils.isNotBlank(inputVO.getName()), SysMenu::getName, inputVO.getName())
                .eq(Objects.nonNull(inputVO.getPcode()), SysMenu::getPcode, inputVO.getPcode())
                .like(StringUtils.isNotBlank(inputVO.getPath()), SysMenu::getPath, inputVO.getPath())
                .like(StringUtils.isNotBlank(inputVO.getComponent()), SysMenu::getComponent, inputVO.getComponent())
                .like(StringUtils.isNotBlank(inputVO.getQuery()), SysMenu::getQuery, inputVO.getQuery())
                .like(StringUtils.isNotBlank(inputVO.getRouteName()), SysMenu::getRouteName, inputVO.getRouteName())
                .eq(Objects.nonNull(inputVO.getIsFrame()), SysMenu::getIsFrame, inputVO.getIsFrame())
                .eq(Objects.nonNull(inputVO.getIsCache()), SysMenu::getIsCache, inputVO.getIsCache())
                .eq(StringUtils.isNotBlank(inputVO.getType()), SysMenu::getType, inputVO.getType())
                .eq(StringUtils.isNotBlank(inputVO.getVisible()), SysMenu::getVisible, inputVO.getVisible())
                .like(StringUtils.isNotBlank(inputVO.getPerms()), SysMenu::getPerms, inputVO.getPerms())
                .like(StringUtils.isNotBlank(inputVO.getIcon()), SysMenu::getIcon, inputVO.getIcon())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysMenu::getStatus, inputVO.getStatus())
                .eq(Objects.nonNull(inputVO.getOrderNum()), SysMenu::getOrderNum, inputVO.getOrderNum())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysMenu::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysMenu::getUpdateDt, inputVO.getUpdateDt())
                .like(StringUtils.isNotBlank(inputVO.getRemark()), SysMenu::getRemark, inputVO.getRemark())
                .orderByAsc(SysMenu::getOrderNum)
                .orderByDesc(SysMenu::getCreateDt);

        sysMenuMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysMenuVO getById(SysMenuGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysMenu menu = sysMenuMapper.selectById(id);
        if (Objects.isNull(menu)) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }
        return convertToVO(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenuCreateInputVO inputVO) {
        // 检查菜单编码是否已存在
        String code = inputVO.getCode();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getCode, code);
        if (sysMenuMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.MENU_ALREADY_EXISTS, code);
        }

        // 检查父菜单是否存在
        Long pcode = inputVO.getPcode();
        if (Objects.nonNull(pcode) && pcode > 0) {
            SysMenu parentMenu = sysMenuMapper.selectById(pcode);
            if (Objects.isNull(parentMenu)) {
                throw new BusinessException(PortalResultCode.MENU_PARENT_NOT_FOUND);
            }
        }

        SysMenu sysMenu = BeanUtils.copyProperties(inputVO, SysMenu::new);
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysMenu existMenu = sysMenuMapper.selectById(id);
        if (Objects.isNull(existMenu)) {
            throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
        }

        // 检查菜单编码是否被其他菜单使用
        String code = inputVO.getCode();
        if (StringUtils.isNotBlank(code) && !Strings.CS.equals(code, existMenu.getCode())) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getCode, code)
                    .ne(SysMenu::getId, id);
            if (sysMenuMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.MENU_ALREADY_EXISTS, code);
            }
        }

        // 检查父菜单是否存在
        Long pcode = inputVO.getPcode();
        if (Objects.nonNull(pcode) && pcode > 0 && !Objects.equals(pcode, existMenu.getPcode())) {
            SysMenu parentMenu = sysMenuMapper.selectById(pcode);
            if (Objects.isNull(parentMenu)) {
                throw new BusinessException(PortalResultCode.MENU_PARENT_NOT_FOUND);
            }

            // 不能将父菜单设置为自己的子菜单
            if (Objects.equals(pcode, id)) {
                throw new BusinessException(PortalResultCode.MENU_PARENT_NOT_FOUND);
            }
        }

        SysMenu sysMenu = BeanUtils.copyProperties(inputVO, SysMenu::new);
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(SysMenuDeleteInputVO inputVO) {
        for (Long id : inputVO.getIds()) {
            SysMenu existMenu = sysMenuMapper.selectById(id);
            if (Objects.isNull(existMenu)) {
                throw new BusinessException(PortalResultCode.MENU_NOT_FOUND);
            }

            // 检查是否存在子菜单
            LambdaQueryWrapper<SysMenu> childWrapper = new LambdaQueryWrapper<>();
            childWrapper.eq(SysMenu::getPcode, id);
            if (sysMenuMapper.exists(childWrapper)) {
                throw new BusinessException(PortalResultCode.MENU_HAS_CHILDREN);
            }

            // 检查菜单是否被角色使用
            LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
            roleMenuWrapper.eq(SysRoleMenu::getMenuCode, existMenu.getCode());
            if (sysRoleMenuMapper.exists(roleMenuWrapper)) {
                throw new BusinessException(PortalResultCode.MENU_HAS_CHILDREN);
            }
        }

        // 物理删除
        sysMenuMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysMenuVO convertToVO(SysMenu menu) {
        if (Objects.isNull(menu)) return null;
        return BeanUtils.copyProperties(menu, SysMenuVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysMenuDTO convertToDTO(SysMenu menu) {
        if (Objects.isNull(menu)) return null;
        return BeanUtils.copyProperties(menu, SysMenuDTO::new);
    }
}
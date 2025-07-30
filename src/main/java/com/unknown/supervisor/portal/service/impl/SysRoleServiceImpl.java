package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.role.SysRoleDTO;
import com.unknown.supervisor.portal.entity.SysRole;
import com.unknown.supervisor.portal.entity.SysRoleMenu;
import com.unknown.supervisor.portal.mapper.SysRoleMapper;
import com.unknown.supervisor.portal.mapper.SysRoleMenuMapper;
import com.unknown.supervisor.portal.service.SysRoleService;
import com.unknown.supervisor.portal.vo.role.*;
import com.unknown.supervisor.utils.BeanUtils;
import com.unknown.supervisor.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统角色服务实现类
 *
 * @author zhongkunming
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    // ========== Controller层调用的公共方法 ==========

    @Override
    public PageResult<SysRoleVO> pageRoles(SysRoleQueryInputVO queryInputVO) {
        IPage<SysRole> page = queryInputVO.toPage();

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(queryInputVO.getRoleCode()), SysRole::getRoleCode, queryInputVO.getRoleCode())
                .like(StringUtils.isNotBlank(queryInputVO.getRoleName()), SysRole::getRoleName, queryInputVO.getRoleName())
                .eq(queryInputVO.getStatus() != null, SysRole::getStatus, queryInputVO.getStatus())
                .orderByAsc(SysRole::getSortOrder)
                .orderByDesc(SysRole::getCreateDt);

        sysRoleMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysRoleVO getRoleById(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }
        return convertToVO(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(SysRoleCreateInputVO createInputVO) {
        // 检查角色编码是否已存在
        if (existsByRoleCode(createInputVO.getRoleCode(), null)) {
            throw new BusinessException(PortalResultCode.ROLE_CODE_EXISTS);
        }

        SysRole entity = BeanUtils.copyProperties(createInputVO, SysRole::new);
        sysRoleMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleUpdateInputVO updateInputVO) {
        // 检查角色是否存在
        SysRole existingRole = sysRoleMapper.selectById(updateInputVO.getId());
        if (existingRole == null) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查角色编码是否已存在（排除自己）
        if (existsByRoleCode(updateInputVO.getRoleCode(), updateInputVO.getId())) {
            throw new BusinessException(PortalResultCode.ROLE_CODE_EXISTS);
        }

        SysRole entity = BeanUtils.copyProperties(updateInputVO, SysRole::new);
        sysRoleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 检查角色是否存在
        SysRole existingRole = sysRoleMapper.selectById(id);
        if (existingRole == null) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 删除角色
        sysRoleMapper.deleteById(id);

        // 删除角色菜单关联关系
        sysRoleMenuMapper.deleteByRoleCode(existingRole.getRoleCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenus(SysRoleMenuAssignInputVO assignInputVO) {
        // 检查角色是否存在
        SysRoleDTO role = getRoleByCode(assignInputVO.getRoleCode());
        if (role == null) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 删除原有的角色菜单关联关系
        sysRoleMenuMapper.deleteByRoleCode(assignInputVO.getRoleCode());

        // 插入新的角色菜单关联关系
        if (CollectionUtils.isNotEmpty(assignInputVO.getMenuCodes())) {
            List<SysRoleMenu> roleMenuList = assignInputVO.getMenuCodes().stream()
                    .map(menuCode -> {
                        SysRoleMenu roleMenu = new SysRoleMenu();
                        roleMenu.setId(IdUtils.nextId());
                        roleMenu.setRoleCode(assignInputVO.getRoleCode());
                        roleMenu.setMenuCode(menuCode);
                        return roleMenu;
                    })
                    .toList();

            sysRoleMenuMapper.insert(roleMenuList);
        }
    }

    @Override
    public List<String> getRoleMenus(SysRoleMenuQueryInputVO queryInputVO) {
        return sysRoleMenuMapper.selectMenuCodesByRoleCode(queryInputVO.getRoleCode());
    }

    @Override
    public List<SysRoleVO> listEnabledRoles() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getStatus, 1)
                .orderByAsc(SysRole::getSortOrder);

        List<SysRole> entityList = sysRoleMapper.selectList(wrapper);
        return entityList.stream()
                .map(this::convertToVO)
                .toList();
    }

    // ========== 内部使用的方法 ==========

    @Override
    public SysRoleDTO getRoleByCode(String roleCode) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>(SysRole.class)
                .eq(SysRole::getRoleCode, roleCode);
        SysRole entity = sysRoleMapper.selectOne(wrapper);
        if (entity == null) {
            return null;
        }
        return this.convertToDTO(entity);
    }

    @Override
    public boolean existsByRoleCode(String roleCode, Long excludeId) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>(SysRole.class)
                .eq(SysRole::getRoleCode, roleCode)
                .ne(excludeId != null, SysRole::getId, excludeId);
        return sysRoleMapper.exists(wrapper);
    }

    private SysRoleDTO convertToDTO(SysRole entity) {
        return BeanUtils.copyProperties(entity, SysRoleDTO::new);
    }

    private SysRoleVO convertToVO(SysRole entity) {
        return BeanUtils.copyProperties(entity, SysRoleVO::new);
    }
}
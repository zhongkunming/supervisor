package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.userrole.SysUserRoleDTO;
import com.unknown.supervisor.portal.entity.SysRole;
import com.unknown.supervisor.portal.entity.SysUser;
import com.unknown.supervisor.portal.entity.SysUserRole;
import com.unknown.supervisor.portal.mapper.SysRoleMapper;
import com.unknown.supervisor.portal.mapper.SysUserMapper;
import com.unknown.supervisor.portal.mapper.SysUserRoleMapper;
import com.unknown.supervisor.portal.service.SysUserRoleService;
import com.unknown.supervisor.portal.vo.userrole.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户角色关联Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public PageResult<SysUserRoleVO> pageQuery(SysUserRoleQueryInputVO inputVO) {
        Page<SysUserRole> page = inputVO.toPage();
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysUserRole::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getOperNo()), SysUserRole::getOperNo, inputVO.getOperNo())
                .like(StringUtils.isNotBlank(inputVO.getRoleCode()), SysUserRole::getRoleCode, inputVO.getRoleCode())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysUserRole::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysUserRole::getUpdateDt, inputVO.getUpdateDt())
                .orderByDesc(SysUserRole::getCreateDt);

        sysUserRoleMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysUserRoleVO getById(SysUserRoleGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysUserRole userRole = sysUserRoleMapper.selectById(id);
        if (Objects.isNull(userRole)) {
            throw new BusinessException(PortalResultCode.USER_ROLE_NOT_FOUND);
        }
        return convertToVO(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserRole(SysUserRoleCreateInputVO inputVO) {
        String operNo = inputVO.getOperNo();
        String roleCode = inputVO.getRoleCode();

        // 检查用户是否存在
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(SysUser::getOperNo, operNo)
                .eq(SysUser::getIsDelete, false);
        if (!sysUserMapper.exists(userWrapper)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查角色是否存在
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRole::getCode, roleCode)
                .eq(SysRole::getIsDelete, false);
        if (!sysRoleMapper.exists(roleWrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查用户角色关联是否已存在
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getOperNo, operNo)
                .eq(SysUserRole::getRoleCode, roleCode);
        if (sysUserRoleMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.USER_ROLE_ALREADY_EXISTS);
        }

        SysUserRole sysUserRole = BeanUtils.copyProperties(inputVO, SysUserRole::new);
        sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(SysUserRoleUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysUserRole existUserRole = sysUserRoleMapper.selectById(id);
        if (Objects.isNull(existUserRole)) {
            throw new BusinessException(PortalResultCode.USER_ROLE_NOT_FOUND);
        }

        String operNo = inputVO.getOperNo();
        String roleCode = inputVO.getRoleCode();

        // 检查用户是否存在
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(SysUser::getOperNo, operNo)
                .eq(SysUser::getIsDelete, false);
        if (!sysUserMapper.exists(userWrapper)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查角色是否存在
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRole::getCode, roleCode)
                .eq(SysRole::getIsDelete, false);
        if (!sysRoleMapper.exists(roleWrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查用户角色关联是否被其他记录使用
        if (!Objects.equals(operNo, existUserRole.getOperNo()) || !Objects.equals(roleCode, existUserRole.getRoleCode())) {
            LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUserRole::getOperNo, operNo)
                    .eq(SysUserRole::getRoleCode, roleCode)
                    .ne(SysUserRole::getId, id);
            if (sysUserRoleMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.USER_ROLE_ALREADY_EXISTS);
            }
        }

        SysUserRole sysUserRole = BeanUtils.copyProperties(inputVO, SysUserRole::new);
        sysUserRoleMapper.updateById(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(SysUserRoleDeleteInputVO inputVO) {
        for (Long id : inputVO.getIds()) {
            SysUserRole existUserRole = sysUserRoleMapper.selectById(id);
            if (Objects.isNull(existUserRole)) {
                throw new BusinessException(PortalResultCode.USER_ROLE_NOT_FOUND);
            }
        }

        // 物理删除
        sysUserRoleMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysUserRoleVO convertToVO(SysUserRole userRole) {
        if (Objects.isNull(userRole)) return null;
        return BeanUtils.copyProperties(userRole, SysUserRoleVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysUserRoleDTO convertToDTO(SysUserRole userRole) {
        if (Objects.isNull(userRole)) return null;
        return BeanUtils.copyProperties(userRole, SysUserRoleDTO::new);
    }
}
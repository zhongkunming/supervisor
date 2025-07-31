package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.role.SysRoleDTO;
import com.unknown.supervisor.portal.entity.SysRole;
import com.unknown.supervisor.portal.entity.SysUserRole;
import com.unknown.supervisor.portal.mapper.SysRoleMapper;
import com.unknown.supervisor.portal.mapper.SysUserRoleMapper;
import com.unknown.supervisor.portal.service.SysRoleService;
import com.unknown.supervisor.portal.vo.role.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 角色信息Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageResult<SysRoleVO> pageQuery(SysRoleQueryInputVO inputVO) {
        Page<SysRole> page = inputVO.toPage();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysRole::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getCode()), SysRole::getCode, inputVO.getCode())
                .like(StringUtils.isNotBlank(inputVO.getName()), SysRole::getName, inputVO.getName())
                .like(StringUtils.isNotBlank(inputVO.getKey()), SysRole::getKey, inputVO.getKey())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysRole::getStatus, inputVO.getStatus())
                .eq(Objects.nonNull(inputVO.getOrderNum()), SysRole::getOrderNum, inputVO.getOrderNum())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysRole::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysRole::getUpdateDt, inputVO.getUpdateDt())
                .like(StringUtils.isNotBlank(inputVO.getRemark()), SysRole::getRemark, inputVO.getRemark())
                .eq(SysRole::getIsDelete, false)
                .orderByAsc(SysRole::getOrderNum)
                .orderByDesc(SysRole::getCreateDt);

        sysRoleMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysRoleVO getById(SysRoleGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysRole role = sysRoleMapper.selectById(id);
        if (Objects.isNull(role) || Boolean.TRUE.equals(role.getIsDelete())) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }
        return convertToVO(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(SysRoleCreateInputVO inputVO) {
        // 检查角色编码是否已存在
        String code = inputVO.getCode();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getCode, code)
                .eq(SysRole::getIsDelete, false);
        if (sysRoleMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.ROLE_ALREADY_EXISTS, code);
        }

        SysRole sysRole = BeanUtils.copyProperties(inputVO, SysRole::new);
        sysRole.setIsDelete(false);
        sysRoleMapper.insert(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysRole existRole = sysRoleMapper.selectById(id);
        if (Objects.isNull(existRole) || Boolean.TRUE.equals(existRole.getIsDelete())) {
            throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
        }

        // 检查角色编码是否被其他角色使用
        String code = inputVO.getCode();
        if (StringUtils.isNotBlank(code) && !Strings.CS.equals(code, existRole.getCode())) {
            LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRole::getCode, code)
                    .ne(SysRole::getId, id)
                    .eq(SysRole::getIsDelete, false);
            if (sysRoleMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.ROLE_ALREADY_EXISTS, code);
            }
        }

        SysRole sysRole = BeanUtils.copyProperties(inputVO, SysRole::new);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(SysRoleDeleteInputVO inputVO) {
        for (Long id : inputVO.getIds()) {
            SysRole existRole = sysRoleMapper.selectById(id);
            if (Objects.isNull(existRole) || Boolean.TRUE.equals(existRole.getIsDelete())) {
                throw new BusinessException(PortalResultCode.ROLE_NOT_FOUND);
            }

            // 检查角色是否正在使用中
            LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
            userRoleWrapper.eq(SysUserRole::getRoleCode, existRole.getCode());
            if (sysUserRoleMapper.exists(userRoleWrapper)) {
                throw new BusinessException(PortalResultCode.ROLE_IN_USE);
            }
        }

        // 逻辑删除
        for (Long id : inputVO.getIds()) {
            SysRole role = new SysRole();
            role.setId(id);
            role.setIsDelete(true);
            sysRoleMapper.updateById(role);
        }
    }

    /**
     * 转换实体类到VO
     */
    private SysRoleVO convertToVO(SysRole role) {
        if (Objects.isNull(role)) return null;
        return BeanUtils.copyProperties(role, SysRoleVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysRoleDTO convertToDTO(SysRole role) {
        if (Objects.isNull(role)) return null;
        return BeanUtils.copyProperties(role, SysRoleDTO::new);
    }
}
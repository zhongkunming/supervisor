package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.user.SysUserCreateInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.dto.user.SysUserQueryInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserUpdateInputDTO;
import com.unknown.supervisor.portal.entity.SysUser;
import com.unknown.supervisor.portal.mapper.SysUserMapper;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.utils.BeanUtils;
import com.unknown.supervisor.utils.PasswdUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    // ========== Controller层调用的公共方法 ==========

    @Override
    public PageResult<SysUserDTO> pageUsers(SysUserQueryInputDTO pageDTO) {
        Page<SysUser> page = pageDTO.toPage();

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageDTO.getOperNo())) {
            wrapper.like(SysUser::getOperNo, pageDTO.getOperNo());
        }
        if (StringUtils.isNotBlank(pageDTO.getUsername())) {
            wrapper.like(SysUser::getUsername, pageDTO.getUsername());
        }
        if (StringUtils.isNotBlank(pageDTO.getRealName())) {
            wrapper.like(SysUser::getRealName, pageDTO.getRealName());
        }
        if (pageDTO.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, pageDTO.getStatus());
        }

        IPage<SysUser> userPage = sysUserMapper.selectPage(page, wrapper);
        return PageResult.trans(userPage, this::convertToDTO);
    }

    @Override
    public SysUserDTO getUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            return null;
        }
        return convertToDTO(user);
    }

    @Override
    public void createUser(SysUserCreateInputDTO createDTO) {
        // 检查操作员编号是否已存在
        if (getUserByOperNo(createDTO.getOperNo()) != null) {
            throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, "操作员编号已存在");
        }

        // 检查用户名是否已存在
        if (getUserByUsername(createDTO.getUsername()) != null) {
            throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, "用户名已存在");
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(createDTO, user);
        // 加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(createDTO.getPassword().getBytes()));

        sysUserMapper.insert(user);
    }

    @Override
    public void updateUser(SysUserUpdateInputDTO updateDTO) {
        SysUser existingUser = sysUserMapper.selectById(updateDTO.getId());
        if (existingUser == null) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查用户名是否已被其他用户使用
        if (StringUtils.isNotBlank(updateDTO.getUsername())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, updateDTO.getUsername())
                    .ne(SysUser::getId, updateDTO.getId());
            if (sysUserMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS);
            }
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(updateDTO, user);

        // 如果有密码更新，进行加密
        if (StringUtils.isNotBlank(updateDTO.getPassword())) {
            user.setPassword(PasswdUtils.encryptPassword(updateDTO.getPassword()));
        }

        sysUserMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }
        sysUserMapper.deleteById(id);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        SysUser updateUser = new SysUser();
        updateUser.setId(id);
        updateUser.setStatus(status);
        sysUserMapper.updateById(updateUser);
    }

    // ========== 内部使用的方法 ==========

    @Override
    public SysUserDTO getUserByOperNo(String operNo) {
        SysUser user = sysUserMapper.selectByOperNo(operNo);
        if (user == null) {
            return null;
        }
        return convertToDTO(user);
    }

    @Override
    public SysUserDTO getUserByUsername(String username) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        return convertToDTO(user);
    }

    private SysUserDTO convertToDTO(SysUser user) {
        return BeanUtils.copyProperties(user, SysUserDTO::new);
    }
}
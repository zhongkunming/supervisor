package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.entity.SysUser;
import com.unknown.supervisor.portal.mapper.SysUserMapper;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.portal.vo.user.SysUserCreateInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserQueryInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserUpdateInputVO;
import com.unknown.supervisor.portal.vo.user.SysUserVO;
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
    public PageResult<SysUserVO> pageUsers(SysUserQueryInputVO queryInputVO) {
        Page<SysUser> page = queryInputVO.toPage();

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(queryInputVO.getOperNo()), SysUser::getOperNo, queryInputVO.getOperNo())
                .like(StringUtils.isNotBlank(queryInputVO.getUsername()), SysUser::getUsername, queryInputVO.getUsername())
                .like(StringUtils.isNotBlank(queryInputVO.getRealName()), SysUser::getRealName, queryInputVO.getRealName())
                .eq(queryInputVO.getStatus() != null, SysUser::getStatus, queryInputVO.getStatus());

        sysUserMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysUserVO getUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null)
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);

        return convertToVO(user);
    }

    @Override
    public void createUser(SysUserCreateInputVO createInputVO) {
        // 检查操作员编号是否已存在
        if (getUserByOperNo(createInputVO.getOperNo()) != null) {
            throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, "操作员编号已存在");
        }

        // 检查用户名是否已存在
        if (getUserByUsername(createInputVO.getUsername()) != null) {
            throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, "用户名已存在");
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(createInputVO, user);
        // 加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(createInputVO.getPassword().getBytes()));

        sysUserMapper.insert(user);
    }

    @Override
    public void updateUser(SysUserUpdateInputVO updateInputVO) {
        // VO转DTO
        SysUser existingUser = sysUserMapper.selectById(updateInputVO.getId());
        if (existingUser == null) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查用户名是否已被其他用户使用
        if (StringUtils.isNotBlank(updateInputVO.getUsername())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, updateInputVO.getUsername())
                    .ne(SysUser::getId, updateInputVO.getId());
            if (sysUserMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS);
            }
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(updateInputVO, user);

        // 如果有密码更新，进行加密
        if (StringUtils.isNotBlank(updateInputVO.getPassword())) {
            user.setPassword(PasswdUtils.encryptPassword(updateInputVO.getPassword()));
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

    private SysUserDTO convertToDTO(SysUser entity) {
        return BeanUtils.copyProperties(entity, SysUserDTO::new);
    }

    private SysUserVO convertToVO(SysUser entity) {
        SysUserVO vo = BeanUtils.copyProperties(entity, SysUserVO::new);
        vo.setStatusName(entity.getStatus() == 1 ? "启用" : "禁用");
        return vo;
    }
}
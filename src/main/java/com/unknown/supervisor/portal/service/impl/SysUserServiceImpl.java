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
import com.unknown.supervisor.portal.vo.user.*;
import com.unknown.supervisor.utils.BeanUtils;
import com.unknown.supervisor.utils.PasswdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户信息Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public PageResult<SysUserVO> pageQuery(SysUserQueryInputVO inputVO) {
        Page<SysUser> page = inputVO.toPage();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.like(StringUtils.isNotBlank(inputVO.getOperNo()), SysUser::getOperNo, inputVO.getOperNo())
                .like(StringUtils.isNotBlank(inputVO.getNickName()), SysUser::getNickName, inputVO.getNickName())
                .like(StringUtils.isNotBlank(inputVO.getPhone()), SysUser::getPhone, inputVO.getPhone())
                .eq(StringUtils.isNotBlank(inputVO.getSex()), SysUser::getSex, inputVO.getSex())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysUser::getStatus, inputVO.getStatus())
                .eq(SysUser::getIsDelete, false)
                .orderByAsc(SysUser::getOrderNum);

        sysUserMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysUserVO getById(SysUserGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysUser user = sysUserMapper.selectById(id);
        if (Objects.isNull(user)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }
        return convertToVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUserCreateInputVO inputVO) {
        // 检查操作员号是否已存在
        String operNo = inputVO.getOperNo();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOperNo, operNo);
        if (sysUserMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, operNo);
        }
        SysUser sysUser = BeanUtils.copyProperties(inputVO, SysUser::new);
        sysUser.setPassword(PasswdUtils.encryptPassword(inputVO.getPassword()));
        sysUser.setPwdUpdateDt(LocalDateTime.now());
        sysUser.setIsDelete(false);
        sysUserMapper.insert(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUserUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysUser existUser = sysUserMapper.selectById(id);
        if (Objects.isNull(existUser)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查操作员号是否被其他用户使用
        String operNo = inputVO.getOperNo();
        if (StringUtils.isNotBlank(operNo) && !Strings.CS.equals(operNo, existUser.getOperNo())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getOperNo, operNo)
                    .ne(SysUser::getId, id);
            if (sysUserMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.USER_ALREADY_EXISTS, operNo);
            }
        }
        SysUser sysUser = BeanUtils.copyProperties(inputVO, SysUser::new);
        if (StringUtils.isNotBlank(inputVO.getPassword())) {
            sysUser.setPassword(PasswdUtils.encryptPassword(inputVO.getPassword()));
            sysUser.setPwdUpdateDt(LocalDateTime.now());
        }
        sysUserMapper.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(SysUserDeleteInputVO inputVO) {
        sysUserMapper.deleteByIds(inputVO.getIds());
    }

    @Override
    public SysUser getUserByOperNo(String operNo) {
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getOperNo, operNo)
                .eq(SysUser::getIsDelete, false));
    }

    /**
     * 转换实体类到VO
     */
    private SysUserVO convertToVO(SysUser user) {
        if (Objects.isNull(user)) return null;
        return BeanUtils.copyProperties(user, SysUserVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysUserDTO convertToDTO(SysUser user) {
        if (Objects.isNull(user)) return null;
        return BeanUtils.copyProperties(user, SysUserDTO::new);
    }
}
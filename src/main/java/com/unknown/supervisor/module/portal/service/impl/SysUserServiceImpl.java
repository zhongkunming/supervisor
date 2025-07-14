package com.unknown.supervisor.module.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.module.portal.common.PortalResultCode;
import com.unknown.supervisor.module.portal.mapper.SysUserMapper;
import com.unknown.supervisor.module.portal.model.SysUser;
import com.unknown.supervisor.module.portal.service.SysUserService;
import com.unknown.supervisor.module.portal.vo.sys.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhongkunming
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public PageResult<UserPageOutputVo> page(UserPageInputVo input) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().orderByAsc(SysUser::getId);
        IPage<SysUser> page = sysUserMapper.selectPage(input.toPage(), wrapper);
        return PageResult.trans(page, UserPageOutputVo::new);
    }

    @Override
    public void add(UserAddInputVo input) {
        String username = input.getUsername();
        if (sysUserMapper.exists(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username))) {
            throw new BusinessException(PortalResultCode.USERNAME_EXIST);
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void edit(UserEditInputVo input) {

    }

    @Override
    public void delete(UserDeleteInputVo input) {

    }
}

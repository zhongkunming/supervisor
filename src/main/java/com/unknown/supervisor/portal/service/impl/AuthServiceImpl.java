package com.unknown.supervisor.portal.service.impl;

import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.mapper.SysUserMapper;
import com.unknown.supervisor.portal.model.SysUser;
import com.unknown.supervisor.portal.service.AuthService;
import com.unknown.supervisor.portal.vo.auth.LoginInputVo;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zhongkunming
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;

    @Override
    public LoginOutputVo login(LoginInputVo input) {
        SysUser sysUser = sysUserMapper.selectByUsername(input.getUsername());
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(PortalResultCode.USER_NOT_EXIST);
        }
        return new LoginOutputVo();
    }
}

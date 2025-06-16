package com.unknown.supervisor.service.impl;

import com.unknown.supervisor.common.ResultCodeBusiness;
import com.unknown.supervisor.framework.exception.BusinessException;
import com.unknown.supervisor.mapper.SysUserMapper;
import com.unknown.supervisor.model.SysUser;
import com.unknown.supervisor.service.AuthService;
import com.unknown.supervisor.vo.auth.LoginInputVo;
import com.unknown.supervisor.vo.auth.LoginOutputVo;
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
            throw new BusinessException(ResultCodeBusiness.USER_NOT_EXIST);
        }

        LoginOutputVo output = new LoginOutputVo();
        return output;
    }
}

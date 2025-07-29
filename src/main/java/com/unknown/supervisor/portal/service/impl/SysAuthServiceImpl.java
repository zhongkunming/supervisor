package com.unknown.supervisor.portal.service.impl;

import com.unknown.supervisor.config.JwtConfig;
import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.auth.LoginInputDTO;
import com.unknown.supervisor.portal.dto.auth.LoginOutputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.service.SysAuthService;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.utils.JwtUtils;
import com.unknown.supervisor.utils.PasswdUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final JwtConfig jwtConfig;

    private final SysUserService sysUserService;

    private final CacheService cacheService;

    // ========== Controller层调用的公共方法 ==========

    @Override
    public LoginOutputDTO login(LoginInputDTO loginInputDTO) {
        // 根据操作员编号查询用户
        String operNo = loginInputDTO.getOperNo();
        SysUserDTO user = sysUserService.getUserByOperNo(operNo);
        if (user == null) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 验证密码
        if (!PasswdUtils.verifyPassword(loginInputDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(PortalResultCode.USER_PASSWORD_INCORRECT);
        }

        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new BusinessException(PortalResultCode.USER_ACCOUNT_LOCKED);
        }

        // 生成JWT令牌
        String token = JwtUtils.generateToken(user.getOperNo());
        cacheService.put(CacheModule.TOKEN, token, operNo, Duration.ofSeconds(jwtConfig.getExpire()));
        // 构建返回结果
        LoginOutputDTO loginOutputDTO = new LoginOutputDTO();
        loginOutputDTO.setToken(token);
        loginOutputDTO.setOperNo(user.getOperNo());
        loginOutputDTO.setUsername(user.getUsername());
        loginOutputDTO.setRealName(user.getRealName());

        return loginOutputDTO;
    }

    @Override
    public void logout() {
        String token = JwtUtils.getToken();
        if (StringUtils.isBlank(token)) return;
        cacheService.delete(CacheModule.TOKEN, token);
    }
}
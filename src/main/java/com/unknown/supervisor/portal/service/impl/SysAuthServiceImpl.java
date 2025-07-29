package com.unknown.supervisor.portal.service.impl;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService sysUserService;

    // ========== Controller层调用的公共方法 ==========

    @Override
    public LoginOutputDTO login(LoginInputDTO loginInputDTO) {
        // 根据操作员编号查询用户
        SysUserDTO user = sysUserService.getUserByOperNo(loginInputDTO.getOperNo());
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
        // 这里可以实现JWT令牌的黑名单机制
        // 目前简单实现，客户端删除token即可
    }
}
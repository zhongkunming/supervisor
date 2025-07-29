package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.dto.auth.LoginInputDTO;
import com.unknown.supervisor.portal.dto.auth.LoginOutputDTO;

public interface SysAuthService {

    /**
     * 用户登录
     */
    LoginOutputDTO login(LoginInputDTO loginInputDTO);

    /**
     * 用户登出
     */
    void logout();
}
package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.vo.auth.LoginInputVO;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVO;

public interface SysAuthService {

    /**
     * 用户登录
     */
    LoginOutputVO login(LoginInputVO loginInputVO);

    /**
     * 用户登出
     */
    void logout();
}
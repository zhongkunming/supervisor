package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.vo.auth.LoginInputVo;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVo;

/**
 * @author zhongkunming
 */
public interface AuthService {

    LoginOutputVo login(LoginInputVo input);
}

package com.unknown.supervisor.module.portal.service;

import com.unknown.supervisor.module.portal.vo.auth.LoginInputVo;
import com.unknown.supervisor.module.portal.vo.auth.LoginOutputVo;

/**
 * @author zhongkunming
 */
public interface AuthService {

    LoginOutputVo login(LoginInputVo input);
}

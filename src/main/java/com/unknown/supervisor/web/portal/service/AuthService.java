package com.unknown.supervisor.web.portal.service;

import com.unknown.supervisor.web.portal.vo.auth.LoginInputVo;
import com.unknown.supervisor.web.portal.vo.auth.LoginOutputVo;

/**
 * @author zhongkunming
 */
public interface AuthService {

    LoginOutputVo login(LoginInputVo input);
}

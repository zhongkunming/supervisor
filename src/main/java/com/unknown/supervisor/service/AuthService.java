package com.unknown.supervisor.service;

import com.unknown.supervisor.vo.auth.LoginInputVo;
import com.unknown.supervisor.vo.auth.LoginOutputVo;

/**
 * @author zhongkunming
 */
public interface AuthService {

    LoginOutputVo login(LoginInputVo input);
}

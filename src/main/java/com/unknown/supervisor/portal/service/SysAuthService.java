package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.vo.auth.LoginInputVO;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVO;
import com.unknown.supervisor.portal.vo.auth.RouterOutputVO;
import com.unknown.supervisor.portal.vo.auth.UserInfoOutputVO;

import java.util.List;

/**
 * 权限认证Service接口
 *
 * @author zhongkunming
 */
public interface SysAuthService {

    /**
     * 用户登录
     *
     * @param inputVO 登录信息
     * @return 登录结果
     */
    LoginOutputVO login(LoginInputVO inputVO);

    /**
     * 用户退出
     */
    void logout();

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    UserInfoOutputVO getUserInfo();

    /**
     * 获取用户路由信息
     *
     * @return 路由信息列表
     */
    List<RouterOutputVO> getRouters();
}
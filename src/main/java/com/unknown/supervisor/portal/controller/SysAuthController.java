package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.portal.service.SysAuthService;
import com.unknown.supervisor.portal.vo.auth.LoginInputVO;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVO;
import com.unknown.supervisor.portal.vo.auth.RouterOutputVO;
import com.unknown.supervisor.portal.vo.auth.UserInfoOutputVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限认证Controller
 *
 * @author zhongkunming
 */
@Tag(name = "权限认证", description = "权限认证相关接口")
@RestController
@RequestMapping("/sys/auth")
@RequiredArgsConstructor
public class SysAuthController {

    private final SysAuthService sysAuthService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public JsonResult<LoginOutputVO> login(@Valid @RequestBody LoginInputVO inputVO) {
        LoginOutputVO outputVO = sysAuthService.login(inputVO);
        return JsonResult.success(outputVO);
    }

    /**
     * 用户退出
     */
    @Operation(summary = "用户退出", description = "用户退出登录接口")
    @PostMapping("/logout")
    public JsonResult<Void> logout() {
        sysAuthService.logout();
        return JsonResult.success();
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    @PostMapping("/getUserInfo")
    public JsonResult<UserInfoOutputVO> getUserInfo() {
        UserInfoOutputVO outputVO = sysAuthService.getUserInfo();
        return JsonResult.success(outputVO);
    }

    /**
     * 获取用户路由信息
     */
    @Operation(summary = "获取路由信息", description = "获取当前用户的路由菜单信息")
    @PostMapping("/getRouters")
    public JsonResult<List<RouterOutputVO>> getRouters() {
        List<RouterOutputVO> routers = sysAuthService.getRouters();
        return JsonResult.success(routers);
    }
}
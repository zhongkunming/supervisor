package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.portal.service.SysAuthService;
import com.unknown.supervisor.portal.vo.auth.LoginInputVO;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/sys/auth")
@RequiredArgsConstructor
public class SysAuthController {

    private final SysAuthService sysAuthService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public JsonResult<LoginOutputVO> login(@Valid @RequestBody LoginInputVO loginInputVO) {
        LoginOutputVO loginOutputVO = sysAuthService.login(loginInputVO);
        return JsonResult.success(loginOutputVO);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public JsonResult<Void> logout() {
        sysAuthService.logout();
        return JsonResult.success();
    }
}
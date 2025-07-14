package com.unknown.supervisor.module.portal.controller;

import com.unknown.supervisor.common.JsonResult;
import com.unknown.supervisor.module.portal.service.AuthService;
import com.unknown.supervisor.module.portal.vo.auth.LoginInputVo;
import com.unknown.supervisor.module.portal.vo.auth.LoginOutputVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongkunming
 */
@Tag(name = "登录授权")
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public JsonResult<LoginOutputVo> login(@Validated @RequestBody LoginInputVo input) {
        return JsonResult.success(authService.login(input));
    }

}

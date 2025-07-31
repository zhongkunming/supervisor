package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.portal.vo.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理")
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询用户列表")
    public JsonResult<PageResult<SysUserVO>> pageQuery(@Valid @RequestBody SysUserQueryInputVO inputVO) {
        PageResult<SysUserVO> result = sysUserService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询用户信息")
    public JsonResult<SysUserVO> getById(@Valid @RequestBody SysUserGetInputVO inputVO) {
        SysUserVO result = sysUserService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("createUser")
    @Operation(summary = "新增用户")
    public JsonResult<Void> createUser(@Valid @RequestBody SysUserCreateInputVO inputVO) {
        sysUserService.createUser(inputVO);
        return JsonResult.success();
    }

    @PostMapping("updateUser")
    @Operation(summary = "修改用户")
    public JsonResult<Void> updateUser(@Valid @RequestBody SysUserUpdateInputVO inputVO) {
        sysUserService.updateUser(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteUser")
    @Operation(summary = "删除用户")
    public JsonResult<Void> deleteUser(@Valid @RequestBody SysUserDeleteInputVO inputVO) {
        sysUserService.deleteUser(inputVO);
        return JsonResult.success();
    }
}
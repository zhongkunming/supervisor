package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysUserRoleService;
import com.unknown.supervisor.portal.vo.userrole.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色关联Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/userRole")
@RequiredArgsConstructor
@Tag(name = "用户角色管理", description = "用户角色管理")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询用户角色关联列表")
    public JsonResult<PageResult<SysUserRoleVO>> pageQuery(@Valid @RequestBody SysUserRoleQueryInputVO inputVO) {
        PageResult<SysUserRoleVO> result = sysUserRoleService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询用户角色关联信息")
    public JsonResult<SysUserRoleVO> getById(@Valid @RequestBody SysUserRoleGetInputVO inputVO) {
        SysUserRoleVO result = sysUserRoleService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createUserRole")
    @Operation(summary = "新增用户角色关联")
    public JsonResult<Void> createUserRole(@Valid @RequestBody SysUserRoleCreateInputVO inputVO) {
        sysUserRoleService.createUserRole(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateUserRole")
    @Operation(summary = "修改用户角色关联")
    public JsonResult<Void> updateUserRole(@Valid @RequestBody SysUserRoleUpdateInputVO inputVO) {
        sysUserRoleService.updateUserRole(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteUserRole")
    @Operation(summary = "删除用户角色关联")
    public JsonResult<Void> deleteUserRole(@Valid @RequestBody SysUserRoleDeleteInputVO inputVO) {
        sysUserRoleService.deleteUserRole(inputVO);
        return JsonResult.success();
    }
}
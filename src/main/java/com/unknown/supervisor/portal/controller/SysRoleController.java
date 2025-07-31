package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysRoleService;
import com.unknown.supervisor.portal.vo.role.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色信息Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "角色管理")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询角色列表")
    public JsonResult<PageResult<SysRoleVO>> pageQuery(@Valid @RequestBody SysRoleQueryInputVO inputVO) {
        PageResult<SysRoleVO> result = sysRoleService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询角色信息")
    public JsonResult<SysRoleVO> getById(@Valid @RequestBody SysRoleGetInputVO inputVO) {
        SysRoleVO result = sysRoleService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createRole")
    @Operation(summary = "新增角色")
    public JsonResult<Void> createRole(@Valid @RequestBody SysRoleCreateInputVO inputVO) {
        sysRoleService.createRole(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateRole")
    @Operation(summary = "修改角色")
    public JsonResult<Void> updateRole(@Valid @RequestBody SysRoleUpdateInputVO inputVO) {
        sysRoleService.updateRole(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteRole")
    @Operation(summary = "删除角色")
    public JsonResult<Void> deleteRole(@Valid @RequestBody SysRoleDeleteInputVO inputVO) {
        sysRoleService.deleteRole(inputVO);
        return JsonResult.success();
    }
}
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

import java.util.List;

/**
 * 系统角色管理控制器
 *
 * @author zhongkunming
 */
@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Operation(summary = "分页查询角色")
    @PostMapping("/query")
    public JsonResult<PageResult<SysRoleVO>> queryRoles(@Valid @RequestBody SysRoleQueryInputVO queryInputVO) {
        PageResult<SysRoleVO> voPage = sysRoleService.pageRoles(queryInputVO);
        return JsonResult.success(voPage);
    }

    @Operation(summary = "根据ID查询角色")
    @PostMapping("/get")
    public JsonResult<SysRoleVO> getRoleById(@Valid @RequestBody SysRoleGetInputVO getInputVO) {
        SysRoleVO roleVO = sysRoleService.getRoleById(getInputVO.getId());
        return JsonResult.success(roleVO);
    }

    @Operation(summary = "创建角色")
    @PostMapping("/create")
    public JsonResult<Void> createRole(@Valid @RequestBody SysRoleCreateInputVO createInputVO) {
        sysRoleService.createRole(createInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "更新角色")
    @PostMapping("/update")
    public JsonResult<Void> updateRole(@Valid @RequestBody SysRoleUpdateInputVO updateInputVO) {
        sysRoleService.updateRole(updateInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "删除角色")
    @PostMapping("/delete")
    public JsonResult<Void> deleteRole(@Valid @RequestBody SysRoleDeleteInputVO deleteInputVO) {
        sysRoleService.deleteRole(deleteInputVO.getId());
        return JsonResult.success();
    }

    @Operation(summary = "分配角色菜单权限")
    @PostMapping("/assign-menus")
    public JsonResult<Void> assignRoleMenus(@Valid @RequestBody SysRoleMenuAssignInputVO assignInputVO) {
        sysRoleService.assignRoleMenus(assignInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "查询角色菜单权限")
    @PostMapping("/menus")
    public JsonResult<List<String>> getRoleMenus(@Valid @RequestBody SysRoleMenuQueryInputVO queryInputVO) {
        List<String> menuCodes = sysRoleService.getRoleMenus(queryInputVO);
        return JsonResult.success(menuCodes);
    }

    @Operation(summary = "查询所有启用角色")
    @PostMapping("/enabled")
    public JsonResult<List<SysRoleVO>> getEnabledRoles() {
        List<SysRoleVO> roleVOList = sysRoleService.listEnabledRoles();
        return JsonResult.success(roleVOList);
    }
}
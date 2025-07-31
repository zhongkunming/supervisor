package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysRoleMenuService;
import com.unknown.supervisor.portal.vo.rolemenu.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色菜单关联Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/role/menu")
@RequiredArgsConstructor
@Tag(name = "角色菜单管理", description = "角色菜单管理")
public class SysRoleMenuController {

    private final SysRoleMenuService sysRoleMenuService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询角色菜单关联列表")
    public JsonResult<PageResult<SysRoleMenuVO>> pageQuery(@Valid @RequestBody SysRoleMenuQueryInputVO inputVO) {
        PageResult<SysRoleMenuVO> result = sysRoleMenuService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询角色菜单关联信息")
    public JsonResult<SysRoleMenuVO> getById(@Valid @RequestBody SysRoleMenuGetInputVO inputVO) {
        SysRoleMenuVO result = sysRoleMenuService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createRoleMenu")
    @Operation(summary = "新增角色菜单关联")
    public JsonResult<Void> createRoleMenu(@Valid @RequestBody SysRoleMenuCreateInputVO inputVO) {
        sysRoleMenuService.createRoleMenu(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateRoleMenu")
    @Operation(summary = "修改角色菜单关联")
    public JsonResult<Void> updateRoleMenu(@Valid @RequestBody SysRoleMenuUpdateInputVO inputVO) {
        sysRoleMenuService.updateRoleMenu(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteRoleMenu")
    @Operation(summary = "删除角色菜单关联")
    public JsonResult<Void> deleteRoleMenu(@Valid @RequestBody SysRoleMenuDeleteInputVO inputVO) {
        sysRoleMenuService.deleteRoleMenu(inputVO);
        return JsonResult.success();
    }
}
package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysMenuService;
import com.unknown.supervisor.portal.vo.menu.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
@Tag(name = "菜单管理", description = "菜单管理")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询菜单列表")
    public JsonResult<PageResult<SysMenuVO>> pageQuery(@Valid @RequestBody SysMenuQueryInputVO inputVO) {
        PageResult<SysMenuVO> result = sysMenuService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询菜单信息")
    public JsonResult<SysMenuVO> getById(@Valid @RequestBody SysMenuGetInputVO inputVO) {
        SysMenuVO result = sysMenuService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createMenu")
    @Operation(summary = "新增菜单")
    public JsonResult<Void> createMenu(@Valid @RequestBody SysMenuCreateInputVO inputVO) {
        sysMenuService.createMenu(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateMenu")
    @Operation(summary = "修改菜单")
    public JsonResult<Void> updateMenu(@Valid @RequestBody SysMenuUpdateInputVO inputVO) {
        sysMenuService.updateMenu(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteMenu")
    @Operation(summary = "删除菜单")
    public JsonResult<Void> deleteMenu(@Valid @RequestBody SysMenuDeleteInputVO inputVO) {
        sysMenuService.deleteMenu(inputVO);
        return JsonResult.success();
    }
}
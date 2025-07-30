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

import java.util.List;

/**
 * 系统菜单管理控制器
 *
 * @author zhongkunming
 */
@Tag(name = "系统菜单管理")
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Operation(summary = "分页查询菜单")
    @PostMapping("/query")
    public JsonResult<PageResult<SysMenuVO>> queryMenus(@Valid @RequestBody SysMenuQueryInputVO queryInputVO) {
        PageResult<SysMenuVO> voPage = sysMenuService.pageMenus(queryInputVO);
        return JsonResult.success(voPage);
    }

    @Operation(summary = "查询菜单树形结构")
    @PostMapping("/tree")
    public JsonResult<List<SysMenuTreeOutputVO>> getMenuTree() {
        List<SysMenuTreeOutputVO> menuTreeVOList = sysMenuService.getMenuTree();
        return JsonResult.success(menuTreeVOList);
    }

    @Operation(summary = "根据ID查询菜单")
    @PostMapping("/get")
    public JsonResult<SysMenuVO> getMenuById(@Valid @RequestBody SysMenuGetInputVO getInputVO) {
        SysMenuVO menuVO = sysMenuService.getMenuById(getInputVO.getId());
        return JsonResult.success(menuVO);
    }

    @Operation(summary = "创建菜单")
    @PostMapping("/create")
    public JsonResult<Void> createMenu(@Valid @RequestBody SysMenuCreateInputVO createInputVO) {
        sysMenuService.createMenu(createInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "更新菜单")
    @PostMapping("/update")
    public JsonResult<Void> updateMenu(@Valid @RequestBody SysMenuUpdateInputVO updateInputVO) {
        sysMenuService.updateMenu(updateInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delete")
    public JsonResult<Void> deleteMenu(@Valid @RequestBody SysMenuDeleteInputVO deleteInputVO) {
        sysMenuService.deleteMenu(deleteInputVO.getId());
        return JsonResult.success();
    }

    @Operation(summary = "查询所有启用菜单")
    @PostMapping("/enabled")
    public JsonResult<List<SysMenuVO>> getEnabledMenus() {
        List<SysMenuVO> menuVOList = sysMenuService.listEnabledMenus();
        return JsonResult.success(menuVOList);
    }
}
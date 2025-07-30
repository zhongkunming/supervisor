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


@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户")
    @PostMapping("/query")
    public JsonResult<PageResult<SysUserVO>> queryUsers(@Valid @RequestBody SysUserQueryInputVO queryInputVO) {
        PageResult<SysUserVO> pageResult = sysUserService.pageUsers(queryInputVO);
        return JsonResult.success(pageResult);
    }

    @Operation(summary = "根据ID查询用户")
    @PostMapping("/get")
    public JsonResult<SysUserVO> getUserById(@Valid @RequestBody SysUserGetByIdInputVO getByIdInputVO) {
        SysUserVO userVO = sysUserService.getUserById(getByIdInputVO.getId());
        return JsonResult.success(userVO);
    }

    @Operation(summary = "创建用户")
    @PostMapping("/create")
    public JsonResult<Void> createUser(@Valid @RequestBody SysUserCreateInputVO createInputVO) {
        sysUserService.createUser(createInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "更新用户")
    @PostMapping("/update")
    public JsonResult<Void> updateUser(@Valid @RequestBody SysUserUpdateInputVO updateInputVO) {
        sysUserService.updateUser(updateInputVO);
        return JsonResult.success();
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    public JsonResult<Void> deleteUser(@Valid @RequestBody SysUserDeleteInputVO deleteVO) {
        sysUserService.deleteUser(deleteVO.getId());
        return JsonResult.success();
    }

    @Operation(summary = "启用/禁用用户")
    @PostMapping("/status")
    public JsonResult<Void> updateUserStatus(@Valid @RequestBody SysUserStatusInputVO statusVO) {
        sysUserService.updateUserStatus(statusVO.getId(), statusVO.getStatus());
        return JsonResult.success();
    }
}
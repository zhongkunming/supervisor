package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.module.portal.vo.sys.*;
import com.unknown.supervisor.portal.vo.sys.*;
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
@Tag(name = "用户管理")
@RestController
@RequestMapping(value = "/sys/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "查询")
    @PostMapping("/page")
    public JsonResult<PageResult<UserPageOutputVo>> page(@Validated @RequestBody UserPageInputVo input) {
        return JsonResult.success(sysUserService.page(input));
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public JsonResult<PageResult<UserPageOutputVo>> add(@Validated @RequestBody UserAddInputVo input) {
        sysUserService.add(input);
        return JsonResult.success();
    }

    @Operation(summary = "修改")
    @PostMapping("/edit")
    public JsonResult<PageResult<UserPageOutputVo>> edit(@Validated @RequestBody UserEditInputVo input) {
        sysUserService.edit(input);
        return JsonResult.success();
    }

    @Operation(summary = "删除")
    @PostMapping("/delete")
    public JsonResult<Void> delete(@Validated @RequestBody UserDeleteInputVo input) {
        sysUserService.delete(input);
        return JsonResult.success();
    }

}

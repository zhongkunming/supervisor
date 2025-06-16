package com.unknown.supervisor.controller;

import com.unknown.supervisor.common.JsonResult;
import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.service.SysUserService;
import com.unknown.supervisor.vo.sys.UserPageInputVo;
import com.unknown.supervisor.vo.sys.UserPageOutputVo;
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

}

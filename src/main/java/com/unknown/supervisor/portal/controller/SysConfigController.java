package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysConfigService;
import com.unknown.supervisor.portal.vo.config.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置Controller
 *
 * @author zhongkunming
 */
@Tag(name = "参数配置管理")
@RestController
@RequestMapping("/portal/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @Operation(summary = "分页查询参数配置列表")
    @PostMapping("/pageQuery")
    public JsonResult<PageResult<SysConfigVO>> pageQuery(@Valid @RequestBody SysConfigQueryInputVO inputVO) {
        PageResult<SysConfigVO> result = sysConfigService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @Operation(summary = "根据ID查询参数配置信息")
    @PostMapping("/getById")
    public JsonResult<SysConfigVO> getById(@Valid @RequestBody SysConfigGetInputVO inputVO) {
        SysConfigVO result = sysConfigService.getById(inputVO);
        return JsonResult.success(result);
    }

    @Operation(summary = "新增参数配置")
    @PostMapping("/createConfig")
    public JsonResult<Void> createConfig(@Valid @RequestBody SysConfigCreateInputVO inputVO) {
        sysConfigService.createConfig(inputVO);
        return JsonResult.success();
    }

    @Operation(summary = "修改参数配置")
    @PostMapping("/updateConfig")
    public JsonResult<Void> updateConfig(@Valid @RequestBody SysConfigUpdateInputVO inputVO) {
        sysConfigService.updateConfig(inputVO);
        return JsonResult.success();
    }

    @Operation(summary = "删除参数配置")
    @PostMapping("/deleteConfig")
    public JsonResult<Void> deleteConfig(@Valid @RequestBody SysConfigDeleteInputVO inputVO) {
        sysConfigService.deleteConfig(inputVO);
        return JsonResult.success();
    }
}
package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysDictTypeService;
import com.unknown.supervisor.portal.vo.dicttype.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典类型Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/dictType")
@RequiredArgsConstructor
@Tag(name = "字典类型管理", description = "字典类型管理")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询字典类型列表")
    public JsonResult<PageResult<SysDictTypeVO>> pageQuery(@Valid @RequestBody SysDictTypeQueryInputVO inputVO) {
        PageResult<SysDictTypeVO> result = sysDictTypeService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询字典类型信息")
    public JsonResult<SysDictTypeVO> getById(@Valid @RequestBody SysDictTypeGetInputVO inputVO) {
        SysDictTypeVO result = sysDictTypeService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createDictType")
    @Operation(summary = "新增字典类型")
    public JsonResult<Void> createDictType(@Valid @RequestBody SysDictTypeCreateInputVO inputVO) {
        sysDictTypeService.createDictType(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateDictType")
    @Operation(summary = "修改字典类型")
    public JsonResult<Void> updateDictType(@Valid @RequestBody SysDictTypeUpdateInputVO inputVO) {
        sysDictTypeService.updateDictType(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteDictType")
    @Operation(summary = "删除字典类型")
    public JsonResult<Void> deleteDictType(@Valid @RequestBody SysDictTypeDeleteInputVO inputVO) {
        sysDictTypeService.deleteDictType(inputVO);
        return JsonResult.success();
    }
}
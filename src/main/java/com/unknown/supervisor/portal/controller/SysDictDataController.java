package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysDictDataService;
import com.unknown.supervisor.portal.vo.dictdata.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典数据Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/dict/data")
@RequiredArgsConstructor
@Tag(name = "字典数据管理", description = "字典数据管理")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询字典数据列表")
    public JsonResult<PageResult<SysDictDataVO>> pageQuery(@Valid @RequestBody SysDictDataQueryInputVO inputVO) {
        PageResult<SysDictDataVO> result = sysDictDataService.pageQuery(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getById")
    @Operation(summary = "根据ID查询字典数据信息")
    public JsonResult<SysDictDataVO> getById(@Valid @RequestBody SysDictDataGetInputVO inputVO) {
        SysDictDataVO result = sysDictDataService.getById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createDictData")
    @Operation(summary = "新增字典数据")
    public JsonResult<Void> createDictData(@Valid @RequestBody SysDictDataCreateInputVO inputVO) {
        sysDictDataService.createDictData(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateDictData")
    @Operation(summary = "修改字典数据")
    public JsonResult<Void> updateDictData(@Valid @RequestBody SysDictDataUpdateInputVO inputVO) {
        sysDictDataService.updateDictData(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteDictData")
    @Operation(summary = "删除字典数据")
    public JsonResult<Void> deleteDictData(@Valid @RequestBody SysDictDataDeleteInputVO inputVO) {
        sysDictDataService.deleteDictData(inputVO);
        return JsonResult.success();
    }
}
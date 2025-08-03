package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.service.SysDictService;
import com.unknown.supervisor.portal.vo.dict.*;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataQueryInputVO;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataVO;
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
 * 字典管理Controller
 *
 * @author zhongkunming
 */
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
@Tag(name = "字典管理", description = "字典管理")
public class SysDictController {

    private final SysDictService sysDictService;

    @PostMapping("/pageQueryDictType")
    @Operation(summary = "分页查询字典类型列表")
    public JsonResult<PageResult<SysDictTypeVO>> pageQueryDictType(@Valid @RequestBody SysDictTypeQueryInputVO inputVO) {
        PageResult<SysDictTypeVO> result = sysDictService.pageQueryDictType(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getTypeById")
    @Operation(summary = "根据ID查询字典类型信息")
    public JsonResult<SysDictTypeVO> getTypeById(@Valid @RequestBody SysDictTypeGetInputVO inputVO) {
        SysDictTypeVO result = sysDictService.getTypeById(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/createDictType")
    @Operation(summary = "新增字典类型")
    public JsonResult<Void> createDictType(@Valid @RequestBody SysDictTypeCreateInputVO inputVO) {
        sysDictService.createDictType(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/updateDictType")
    @Operation(summary = "修改字典类型")
    public JsonResult<Void> updateDictType(@Valid @RequestBody SysDictTypeUpdateInputVO inputVO) {
        sysDictService.updateDictType(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/deleteDictType")
    @Operation(summary = "删除字典类型")
    public JsonResult<Void> deleteDictType(@Valid @RequestBody SysDictTypeDeleteInputVO inputVO) {
        sysDictService.deleteDictType(inputVO);
        return JsonResult.success();
    }

    @PostMapping("/exportDictType")
    @Operation(summary = "导出字典类型")
    public void exportDictType(@Valid @RequestBody SysDictTypeExportInputVO inputVO) {
        sysDictService.exportDictType(inputVO);
    }

    @PostMapping("/refreshCache")
    @Operation(summary = "刷新字典类型缓存")
    public void refreshCache() {
        sysDictService.refreshCache();
    }

    @PostMapping("/getDictData")
    @Operation(summary = "查询字典数据列表")
    public JsonResult<List<SysDictDataVO>> getDictData(@Valid @RequestBody SysDictDateListInputVO inputVO) {
        List<SysDictDataVO> result = sysDictService.getDictData(inputVO);
        return JsonResult.success(result);
    }

    @PostMapping("/getDictTypeListAll")
    @Operation(summary = "查询全部字典类型列表")
    public JsonResult<List<SysDictTypeVO>> getDictTypeListAll() {
        List<SysDictTypeVO> result = sysDictService.getDictTypeListAll();
        return JsonResult.success(result);
    }

    @PostMapping("/pageQueryDictData")
    @Operation(summary = "分页查询字典数据列表")
    public JsonResult<PageResult<SysDictDataVO>> pageQueryDictData(@Valid @RequestBody SysDictDataQueryInputVO inputVO) {
        PageResult<SysDictDataVO> result = sysDictService.pageQueryDictData(inputVO);
        return JsonResult.success(result);
    }
}
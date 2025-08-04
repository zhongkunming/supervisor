package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.dict.SysDictDataDTO;
import com.unknown.supervisor.portal.vo.dict.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 字典类型Service接口
 *
 * @author zhongkunming
 */
public interface SysDictService {

    /**
     * 分页查询字典类型列表
     *
     * @param inputVO 查询条件
     * @return 字典类型列表
     */
    PageResult<SysDictTypeVO> pageQueryDictType(SysDictTypeQueryInputVO inputVO);

    /**
     * 根据ID查询字典类型信息
     *
     * @param inputVO 查询字典类型
     * @return 字典类型信息
     */
    SysDictTypeVO getDictTypeById(SysDictTypeGetInputVO inputVO);

    /**
     * 新增字典类型
     *
     * @param inputVO 字典类型信息
     */
    void createDictType(SysDictTypeCreateInputVO inputVO);

    /**
     * 修改字典类型
     *
     * @param inputVO 字典类型信息
     */
    void updateDictType(SysDictTypeUpdateInputVO inputVO);

    /**
     * 删除字典类型
     *
     * @param inputVO 字典类型ID列表
     */
    void deleteDictType(SysDictTypeDeleteInputVO inputVO);

    void exportDictType(SysDictTypeExportInputVO inputVO);

    void refreshCache();

    List<SysDictDataVO> getDictData(SysDictDateListInputVO inputVO);

    List<SysDictTypeVO> getDictTypeListAll();

    /**
     * 分页查询字典数据列表
     *
     * @param inputVO 查询条件
     * @return 字典数据列表
     */
    PageResult<SysDictDataVO> pageQueryDictData(SysDictDataQueryInputVO inputVO);

    /**
     * 根据ID查询字典数据信息
     *
     * @param inputVO 查询字典数据
     * @return 字典数据信息
     */
    SysDictDataVO getDictDataById(SysDictDataGetInputVO inputVO);

    /**
     * 新增字典数据
     *
     * @param inputVO 字典数据信息
     */
    void createDictData(SysDictDataCreateInputVO inputVO);

    /**
     * 修改字典数据
     *
     * @param inputVO 字典数据信息
     */
    void updateDictData(SysDictDataUpdateInputVO inputVO);

    /**
     * 删除字典数据
     *
     * @param inputVO 字典数据ID列表
     */
    void deleteDictData(SysDictDataDeleteInputVO inputVO);

    void exportDictData(SysDictDataExportInputVO inputVO);
}
package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.dict.*;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataQueryInputVO;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataVO;

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
    SysDictTypeVO getTypeById(SysDictTypeGetInputVO inputVO);

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

    PageResult<SysDictDataVO> pageQueryDictData(SysDictDataQueryInputVO inputVO);
}
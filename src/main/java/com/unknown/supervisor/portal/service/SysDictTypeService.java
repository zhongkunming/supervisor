package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.dicttype.*;

/**
 * 字典类型Service接口
 *
 * @author zhongkunming
 */
public interface SysDictTypeService {

    /**
     * 分页查询字典类型列表
     *
     * @param inputVO 查询条件
     * @return 字典类型列表
     */
    PageResult<SysDictTypeVO> pageQuery(SysDictTypeQueryInputVO inputVO);

    /**
     * 根据ID查询字典类型信息
     *
     * @param inputVO 查询字典类型
     * @return 字典类型信息
     */
    SysDictTypeVO getById(SysDictTypeGetInputVO inputVO);

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
}
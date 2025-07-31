package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.dictdata.*;

/**
 * 字典数据Service接口
 *
 * @author zhongkunming
 */
public interface SysDictDataService {

    /**
     * 分页查询字典数据列表
     *
     * @param inputVO 查询条件
     * @return 字典数据列表
     */
    PageResult<SysDictDataVO> pageQuery(SysDictDataQueryInputVO inputVO);

    /**
     * 根据ID查询字典数据信息
     *
     * @param inputVO 查询字典数据
     * @return 字典数据信息
     */
    SysDictDataVO getById(SysDictDataGetInputVO inputVO);

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
}
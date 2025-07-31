package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.config.*;

/**
 * 参数配置Service接口
 *
 * @author zhongkunming
 */
public interface SysConfigService {

    /**
     * 分页查询参数配置列表
     *
     * @param inputVO 查询条件
     * @return 参数配置列表
     */
    PageResult<SysConfigVO> pageQuery(SysConfigQueryInputVO inputVO);

    /**
     * 根据ID查询参数配置信息
     *
     * @param inputVO 查询参数配置
     * @return 参数配置信息
     */
    SysConfigVO getById(SysConfigGetInputVO inputVO);

    /**
     * 新增参数配置
     *
     * @param inputVO 参数配置信息
     */
    void createConfig(SysConfigCreateInputVO inputVO);

    /**
     * 修改参数配置
     *
     * @param inputVO 参数配置信息
     */
    void updateConfig(SysConfigUpdateInputVO inputVO);

    /**
     * 删除参数配置
     *
     * @param inputVO 参数配置ID列表
     */
    void deleteConfig(SysConfigDeleteInputVO inputVO);
}
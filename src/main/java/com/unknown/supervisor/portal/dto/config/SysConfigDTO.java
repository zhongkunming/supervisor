package com.unknown.supervisor.portal.dto.config;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 参数配置DTO
 *
 * @author zhongkunming
 */
@Data
public class SysConfigDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 参数键名
     */
    private String key;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键值
     */
    private String value;

    /**
     * 系统内置（Y是 N否）
     */
    private String type;

    /**
     * 创建人
     */
    private String createOperNo;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新人
     */
    private String updateOperNo;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    private String remark;
}
package com.unknown.supervisor.portal.dto.dicttype;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型DTO
 *
 * @author zhongkunming
 */
@Data
public class SysDictTypeDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

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
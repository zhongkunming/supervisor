package com.unknown.supervisor.portal.dto.dict;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据DTO
 *
 * @author zhongkunming
 */
@Data
public class SysDictDataDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认
     */
    private Boolean isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 字典排序
     */
    private Integer orderNum;

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
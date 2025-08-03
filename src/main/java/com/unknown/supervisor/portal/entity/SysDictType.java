package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 字典类型表
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_type")
public class SysDictType {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createOperNo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDt;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateOperNo;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    private String remark;
}
package com.unknown.supervisor.portal.dto.dict;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型DTO
 *
 * @author zhongkunming
 */
@Data
@ExcelIgnoreUnannotated
public class SysDictTypeDTO {

    /**
     * 主键ID
     */
    @ExcelProperty("主键")
    private Long id;

    /**
     * 字典类型
     */
    @ExcelProperty("字典类型")
    private String type;

    /**
     * 字典名称
     */
    @ExcelProperty("字典名称")
    private String name;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态")
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
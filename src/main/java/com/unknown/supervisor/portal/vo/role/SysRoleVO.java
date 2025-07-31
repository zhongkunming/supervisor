package com.unknown.supervisor.portal.vo.role;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色VO
 *
 * @author zhongkunming
 */
@Data
public class SysRoleVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String key;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 删除标志
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    private String remark;
}
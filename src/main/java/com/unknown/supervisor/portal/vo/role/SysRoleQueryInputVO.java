package com.unknown.supervisor.portal.vo.role;

import com.unknown.supervisor.core.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 查询角色VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleQueryInputVO extends PageRequest {

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
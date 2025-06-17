package com.unknown.supervisor.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author zhongkunming
 */
@Schema(description = "用户表")
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 性别 male:男性; female:女性; unknown:未知
     */
    @TableField(value = "gender")
    @Schema(description = "性别 male:男性; female:女性; unknown:未知")
    private String gender;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @Schema(description = "头像")
    private String avatar;

    /**
     * 状态 enable:启用; ban:禁用; delete:删除
     */
    @TableField(value = "status")
    @Schema(description = "状态 enable:启用; ban:禁用; delete:删除")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "created_dt")
    @Schema(description = "创建时间")
    private Date createdDt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_dt")
    @Schema(description = "更新时间")
    private Date updatedDt;
}
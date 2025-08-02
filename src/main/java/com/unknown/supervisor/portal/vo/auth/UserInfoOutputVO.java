package com.unknown.supervisor.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "用户信息VO")
public class UserInfoOutputVO {

    /**
     * 用户基本信息
     */
    @Schema(description = "用户信息")
    private UserVO user;

    /**
     * 用户角色列表
     */
    @Schema(description = "用户角色列表")
    private List<String> roles;

    /**
     * 用户权限列表
     */
    @Schema(description = "用户权限列表")
    private List<String> permissions;

    @Data
    @Schema(description = "用户信息")
    public static class UserVO {

        /**
         * 主键ID
         */
        @Schema(description = "主键ID")
        private Long id;

        /**
         * 操作员号
         */
        @Schema(description = "操作员号")
        private String operNo;

        /**
         * 用户昵称
         */
        @Schema(description = "用户昵称")
        private String nickName;

        /**
         * 头像地址
         */
        @Schema(description = "头像地址")
        private String avatar;
    }
}
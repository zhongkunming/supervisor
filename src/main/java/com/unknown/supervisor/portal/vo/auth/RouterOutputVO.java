package com.unknown.supervisor.portal.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 路由信息VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "路由信息VO")
public class RouterOutputVO {

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")
    private String name;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String query;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirect;

    /**
     * 路由元信息
     */
    @Schema(description = "路由元信息")
    private MetaVO meta;

    /**
     * 子路由
     */
    @Schema(description = "子路由")
    private List<RouterOutputVO> children;

    /**
     * 路由元信息
     */
    @Data
    @Schema(description = "路由元信息")
    public static class MetaVO {

        /**
         * 菜单标题
         */
        @Schema(description = "菜单标题")
        private String title;

        /**
         * 菜单图标
         */
        @Schema(description = "菜单图标")
        private String icon;

        /**
         * 是否不缓存
         */
        @Schema(description = "是否不缓存")
        private Boolean noCache;

        /**
         * 内链地址
         */
        @Schema(description = "内链地址")
        private String link;
    }
}
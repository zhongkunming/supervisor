package com.unknown.supervisor.portal.vo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    @Schema(description = "组件页面")
    private Boolean alwaysShow;

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
         * 是否缓存
         */
        @Schema(description = "是否缓存")
        private Boolean isCache;

        /**
         * 内链地址
         */
        @Schema(description = "内链地址")
        private String link;
    }
}
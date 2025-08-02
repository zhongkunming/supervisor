package com.unknown.supervisor.portal.service.impl;

import com.unknown.supervisor.config.JwtConfig;
import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.entity.SysUser;
import com.unknown.supervisor.portal.service.SysAuthService;
import com.unknown.supervisor.portal.service.SysPermService;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.portal.vo.auth.LoginInputVO;
import com.unknown.supervisor.portal.vo.auth.LoginOutputVO;
import com.unknown.supervisor.portal.vo.auth.RouterOutputVO;
import com.unknown.supervisor.portal.vo.auth.UserInfoOutputVO;
import com.unknown.supervisor.utils.BeanUtils;
import com.unknown.supervisor.utils.JwtUtils;
import com.unknown.supervisor.utils.PasswdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Strings.CS;

/**
 * 权限认证Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final JwtConfig jwtConfig;
    private final CacheService cacheService;
    private final SysUserService sysUserService;
    private final SysPermService sysPermService;

    @Override
    public LoginOutputVO login(LoginInputVO inputVO) {
        String operNo = inputVO.getOperNo();
        String password = inputVO.getPassword();

        // 查询用户信息
        SysUser sysUser = sysUserService.getUserByOperNo(operNo);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }

        // 检查用户状态
        if (!CS.equals(sysUser.getStatus(), "0")) {
            throw new BusinessException(PortalResultCode.USER_DISABLED);
        }

        // 验证密码
        if (!PasswdUtils.verifyPassword(password, sysUser.getPassword())) {
            throw new BusinessException(PortalResultCode.USER_PASSWORD_ERROR);
        }

        // 生成JWT令牌
        String token = JwtUtils.generateToken(operNo);
        // 将令牌存储到缓存中
        cacheService.put(CacheModule.TOKEN, token, operNo, Duration.ofSeconds(jwtConfig.getExpire()));

        // 构建返回结果
        LoginOutputVO outputVO = new LoginOutputVO();
        outputVO.setToken(token);
        return outputVO;
    }

    @Override
    public void logout() {
        try {
            String token = JwtUtils.getToken();
            // 从缓存中删除令牌
            cacheService.delete(CacheModule.TOKEN, token);
        } catch (Exception e) {
            log.warn("退出登录时删除令牌失败: {}", e.getMessage());
        }
    }

    @Override
    public UserInfoOutputVO getUserInfo() {
        String operNo = JwtUtils.getOperNo();
        // 查询用户信息
        SysUser sysUser = sysUserService.getUserByOperNo(operNo);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(PortalResultCode.USER_NOT_FOUND);
        }
        UserInfoOutputVO outputVO = new UserInfoOutputVO();
        outputVO.setUser(BeanUtils.copyProperties(sysUser, UserInfoOutputVO.UserVO::new));
        // 获取用户角色
        outputVO.setRoles(sysPermService.getRolePerms(operNo));
        // 获取菜单权限
        outputVO.setPermissions(sysPermService.getMenuPerms(operNo));
        return outputVO;
    }

    @Override
    public List<RouterOutputVO> getRouters() {
        String operNo = JwtUtils.getOperNo();

        // 获取用户菜单权限
        List<SysMenu> menus = sysPermService.getUserMenus(operNo);
        // 先构建菜单树结构
        List<SysMenu> menusTree = sysPermService.buildMenusToTree(menus);
        // 构建路由树
        return buildRouters(menusTree);
    }

    /**
     * 构建路由树
     */
    private List<RouterOutputVO> buildRouters(List<SysMenu> menus) {
        List<RouterOutputVO> routers = new ArrayList<>();
        for (SysMenu menu : menus) {
            RouterOutputVO router = new RouterOutputVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());

            // 构建meta信息
            RouterOutputVO.MetaVO meta = new RouterOutputVO.MetaVO();
            meta.setTitle(menu.getName());
            meta.setIcon(menu.getIcon());
            meta.setIsCache(menu.getIsCache());
            if (isHttpUrl(menu.getPath())) meta.setLink(menu.getPath());
            router.setMeta(meta);
            List<SysMenu> menuChildren = menu.getChildren();
            if (CollectionUtils.isNotEmpty(menuChildren) && CS.equals(menu.getType(), "M")) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildRouters(menuChildren));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterOutputVO> childrenList = new ArrayList<>();
                RouterOutputVO children = new RouterOutputVO();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(getRouteName(menu.getRouteName(), menu.getPath()));

                RouterOutputVO.MetaVO childMeta = new RouterOutputVO.MetaVO();
                childMeta.setTitle(menu.getName());
                childMeta.setIcon(menu.getIcon());
                childMeta.setIsCache(menu.getIsCache());
                if (isHttpUrl(menu.getPath())) childMeta.setLink(menu.getPath());
                children.setMeta(childMeta);
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (CS.equals(menu.getPcode(), "0") && isInnerLink(menu)) {
                RouterOutputVO.MetaVO linkMeta = new RouterOutputVO.MetaVO();
                linkMeta.setTitle(menu.getName());
                linkMeta.setIcon(menu.getIcon());
                router.setMeta(linkMeta);
                router.setPath("/");

                List<RouterOutputVO> childrenList = new ArrayList<>();
                RouterOutputVO children = new RouterOutputVO();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent("InnerLink");
                children.setName(getRouteName(menu.getRouteName(), routerPath));

                RouterOutputVO.MetaVO innerMeta = new RouterOutputVO.MetaVO();
                innerMeta.setTitle(menu.getName());
                innerMeta.setIcon(menu.getIcon());
                innerMeta.setLink(menu.getPath());
                children.setMeta(innerMeta);
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     */
    private String getRouteName(SysMenu menu) {
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            return StringUtils.EMPTY;
        }
        return getRouteName(menu.getRouteName(), menu.getPath());
    }

    /**
     * 获取路由名称，如没有配置路由名称则取路由地址
     */
    private String getRouteName(String name, String path) {
        String routerName = StringUtils.isNotBlank(name) ? name : path;
        return StringUtils.capitalize(routerName);
    }

    /**
     * 获取路由地址
     */
    private String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (!CS.equals(menu.getPcode(), "0") && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (CS.equals(menu.getPcode(), "0") && CS.equals(menu.getType(), "M") && menu.getIsFrame()) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     */
    private String getComponent(SysMenu menu) {
        String component = "Layout";
        if (StringUtils.isNotBlank(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isBlank(menu.getComponent()) && !CS.equals(menu.getPcode(), "0") && isInnerLink(menu)) {
            component = "InnerLink";
        } else if (StringUtils.isBlank(menu.getComponent()) && isParentView(menu)) {
            component = "ParentView";
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     */
    private boolean isMenuFrame(SysMenu menu) {
        return CS.equals(menu.getPcode(), "0") && CS.equals(menu.getType(), "C") && menu.getIsFrame();
    }

    /**
     * 是否为parent_view组件
     */
    private boolean isParentView(SysMenu menu) {
        return !CS.equals(menu.getPcode(), "0") && CS.equals(menu.getType(), "M");
    }

    /**
     * 是否为内链组件
     */
    private boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame() && isHttpUrl(menu.getPath());
    }

    /**
     * 判断是否为http(s)://开头
     */
    private boolean isHttpUrl(String link) {
        return CS.startsWithAny(link, "http://", "https://");
    }

    /**
     * 内链域名特殊字符替换
     */
    private String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path,
                new String[]{"http://", "https://", "www.", ".", ":"},
                new String[]{"", "", "", "/", "/"});
    }
}
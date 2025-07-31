package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.entity.*;
import com.unknown.supervisor.portal.mapper.*;
import com.unknown.supervisor.portal.service.SysAuthService;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权限认证Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;
    private final CacheService cacheService;

    private final SysUserService sysUserService;

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
        if (!Strings.CS.equals(sysUser.getStatus(), "0")) {
            throw new BusinessException(PortalResultCode.USER_DISABLED);
        }

        // 验证密码
        if (!PasswdUtils.verifyPassword(password, sysUser.getPassword())) {
            throw new BusinessException(PortalResultCode.USER_PASSWORD_ERROR);
        }

        // 生成JWT令牌
        String token = JwtUtils.generateToken(operNo);
        // 将令牌存储到缓存中
        cacheService.put(CacheModule.TOKEN, token, operNo, Duration.ofHours(12));

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

        UserInfoOutputVO outputVO = BeanUtils.copyProperties(sysUser, UserInfoOutputVO::new);
        // 获取用户角色
        List<String> roles = getUserRoles(operNo);
        outputVO.setRoles(roles);

        // 获取用户权限
        List<String> permissions = getUserPermissions(operNo);
        outputVO.setPermissions(permissions);

        return outputVO;
    }

    @Override
    public List<RouterOutputVO> getRouters() {
        String operNo = JwtUtils.getOperNo();

        // 获取用户菜单权限
        List<SysMenu> menus = getUserMenus(operNo);

        // 构建路由树
        return buildRouters(menus);
    }

    /**
     * 获取用户角色列表
     */
    private List<String> getUserRoles(String operNo) {
        // 查询用户角色关联
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysUserRole::getOperNo, operNo);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(userRoleWrapper);

        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取角色编码列表
        List<String> roleCodes = userRoles.stream()
                .map(SysUserRole::getRoleCode)
                .collect(Collectors.toList());

        // 查询角色信息
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.in(SysRole::getCode, roleCodes)
                .eq(SysRole::getStatus, "0")
                .eq(SysRole::getIsDelete, false);
        List<SysRole> roles = sysRoleMapper.selectList(roleWrapper);

        return roles.stream()
                .map(SysRole::getKey)
                .collect(Collectors.toList());
    }

    /**
     * 获取用户权限列表
     */
    private List<String> getUserPermissions(String operNo) {
        List<SysMenu> menus = getUserMenus(operNo);

        return menus.stream()
                .map(SysMenu::getPerms)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取用户菜单列表
     */
    private List<SysMenu> getUserMenus(String operNo) {
        // 查询用户角色关联
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysUserRole::getOperNo, operNo);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(userRoleWrapper);

        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取角色编码列表
        List<String> roleCodes = userRoles.stream()
                .map(SysUserRole::getRoleCode)
                .collect(Collectors.toList());

        // 查询角色菜单关联
        LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
        roleMenuWrapper.in(SysRoleMenu::getRoleCode, roleCodes);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(roleMenuWrapper);

        if (roleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取菜单编码列表
        List<String> menuCodes = roleMenus.stream()
                .map(SysRoleMenu::getMenuCode)
                .distinct()
                .collect(Collectors.toList());

        // 查询菜单信息
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.in(SysMenu::getCode, menuCodes)
                .eq(SysMenu::getStatus, "0")
                .orderByAsc(SysMenu::getOrderNum);

        return sysMenuMapper.selectList(menuWrapper);
    }

    /**
     * 构建路由树
     */
    private List<RouterOutputVO> buildRouters(List<SysMenu> menus) {
        List<RouterOutputVO> routers = new ArrayList<>();

        // 先找出所有的父级菜单
        List<SysMenu> rootMenus = menus.stream()
                .filter(menu -> Objects.isNull(menu.getPcode()) || Strings.CS.equals(menu.getPcode(), "0"))
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .toList();

        // 递归构建路由树
        for (SysMenu rootMenu : rootMenus) {
            RouterOutputVO router = buildRouter(rootMenu, menus);
            routers.add(router);
        }

        return routers;
    }

    /**
     * 构建单个路由
     */
    private RouterOutputVO buildRouter(SysMenu menu, List<SysMenu> allMenus) {
        RouterOutputVO router = new RouterOutputVO();
        router.setName(menu.getRouteName());
        router.setPath(menu.getPath());
        router.setComponent(menu.getComponent());
        router.setQuery(menu.getQuery());
        router.setHidden("1".equals(menu.getVisible()));

        // 构建meta信息
        RouterOutputVO.MetaVO meta = new RouterOutputVO.MetaVO();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setNoCache(!Boolean.TRUE.equals(menu.getIsCache()));
        if (Boolean.TRUE.equals(menu.getIsFrame())) {
            meta.setLink(menu.getPath());
        }
        router.setMeta(meta);

        // 查找子菜单
        List<SysMenu> childMenus = allMenus.stream()
                .filter(childMenu -> Strings.CS.equals(childMenu.getPcode(), menu.getCode()))
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .toList();

        if (!childMenus.isEmpty()) {
            List<RouterOutputVO> children = new ArrayList<>();
            for (SysMenu childMenu : childMenus) {
                RouterOutputVO childRouter = buildRouter(childMenu, allMenus);
                children.add(childRouter);
            }
            router.setChildren(children);
        }

        return router;
    }
}
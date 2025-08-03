package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unknown.supervisor.portal.entity.SysMenu;
import com.unknown.supervisor.portal.entity.SysRole;
import com.unknown.supervisor.portal.mapper.SysMenuMapper;
import com.unknown.supervisor.portal.mapper.SysPermMapper;
import com.unknown.supervisor.portal.service.SysPermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.apache.commons.lang3.Strings.CS;

/**
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPermServiceImpl implements SysPermService {

    private final static String ADMIN_ROLE_KEY = "admin";
    private final static String ADMIN_PERMS = "*:*:*";
    private final SysPermMapper sysPermMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public Boolean isAdmin(String permission) {
        return getRolePerms(permission).contains(ADMIN_ROLE_KEY);
    }

    @Override
    public List<String> getRolePerms(String operNo) {
        List<SysRole> roles = sysPermMapper.selectRolesByOperNo(operNo);
        if (roles.stream().anyMatch(val -> CS.contains(val.getKey(), ADMIN_ROLE_KEY))) {
            return Collections.singletonList(ADMIN_ROLE_KEY);
        }
        return roles.stream()
                .map(SysRole::getKey)
                .filter(StringUtils::isNotBlank)
                .flatMap(val -> Arrays.stream(StringUtils.split(val, ",")))
                .distinct()
                .toList();
    }

    @Override
    public List<String> getMenuPerms(String operNo) {
        if (isAdmin(operNo)) {
            return Collections.singletonList(ADMIN_PERMS);
        }
        List<SysRole> roles = sysPermMapper.selectRolesByOperNo(operNo);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return sysPermMapper.selectMenuPermsByOperNo(operNo);
    }

    @Override
    public List<SysMenu> getUserMenus(String operNo) {
        List<SysMenu> sysMenus;
        if (isAdmin(operNo)) {
            LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
            menuWrapper.in(SysMenu::getType, "M", "C");
            menuWrapper.eq(SysMenu::getStatus, "0");
            menuWrapper.orderByAsc(SysMenu::getPcode);
            menuWrapper.orderByAsc(SysMenu::getOrderNum);
            sysMenus = sysMenuMapper.selectList(menuWrapper);
        } else {
            sysMenus = sysPermMapper.selectMenusByOperNo(operNo);
        }
        return sysMenus;
    }

    @Override
    public List<SysMenu> buildMenusToTree(List<SysMenu> menus) {
        List<SysMenu> rootMenus = new ArrayList<>();
        for (SysMenu menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (CS.equals(menu.getPcode(), "0")) {
                recursionFn(menus, menu);
                rootMenus.add(menu);
            }
        }
        return rootMenus;
    }

    private void recursionFn(List<SysMenu> menus, SysMenu menu) {
        // 得到子节点列表
        List<SysMenu> childList = getChildMenus(menus, menu.getCode());
        menu.setChildren(childList);
        for (SysMenu child : childList) {
            if (hasChild(menus, child)) {
                recursionFn(menus, child);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildMenus(List<SysMenu> menus, String pcode) {
        return menus.stream()
                .filter(menu -> CS.equals(menu.getPcode(), pcode))
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .toList();
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> menus, SysMenu menu) {
        return CollectionUtils.isNotEmpty(getChildMenus(menus, menu.getCode()));
    }
}

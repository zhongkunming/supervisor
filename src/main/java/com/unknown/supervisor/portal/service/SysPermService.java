package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.portal.entity.SysMenu;

import java.util.List;

/**
 * @author zhongkunming
 */
public interface SysPermService {

    Boolean isAdmin(String permission);

    List<String> getRolePerms(String operNo);

    List<String> getMenuPerms(String operNo);

    List<SysMenu> getUserMenus(String operNo);

    List<SysMenu> buildMenusToTree(List<SysMenu> menus);
}

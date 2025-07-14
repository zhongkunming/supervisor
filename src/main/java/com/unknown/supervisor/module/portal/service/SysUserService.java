package com.unknown.supervisor.module.portal.service;

import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.module.portal.vo.sys.*;

/**
 * @author zhongkunming
 */
public interface SysUserService {

    PageResult<UserPageOutputVo> page(UserPageInputVo input);

    void add(UserAddInputVo input);

    void edit(UserEditInputVo input);

    void delete(UserDeleteInputVo input);
}

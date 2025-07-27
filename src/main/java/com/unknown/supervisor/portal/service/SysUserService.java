package com.unknown.supervisor.portal.service;

import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.vo.sys.*;

/**
 * @author zhongkunming
 */
public interface SysUserService {

    PageResult<UserPageOutputVo> page(UserPageInputVo input);

    void add(UserAddInputVo input);

    void edit(UserEditInputVo input);

    void delete(UserDeleteInputVo input);
}

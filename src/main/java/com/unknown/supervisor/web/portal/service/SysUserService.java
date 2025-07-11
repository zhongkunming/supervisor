package com.unknown.supervisor.web.portal.service;

import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.web.portal.vo.sys.*;

/**
 * @author zhongkunming
 */
public interface SysUserService {

    PageResult<UserPageOutputVo> page(UserPageInputVo input);

    void add(UserAddInputVo input);

    void edit(UserEditInputVo input);

    void delete(UserDeleteInputVo input);
}

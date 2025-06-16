package com.unknown.supervisor.service;

import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.vo.sys.UserPageInputVo;
import com.unknown.supervisor.vo.sys.UserPageOutputVo;

/**
 * @author zhongkunming
 */
public interface SysUserService {

    PageResult<UserPageOutputVo> page(UserPageInputVo input);
}

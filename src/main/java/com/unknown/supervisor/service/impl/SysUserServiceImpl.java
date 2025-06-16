package com.unknown.supervisor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.unknown.supervisor.common.PageResult;
import com.unknown.supervisor.mapper.SysUserMapper;
import com.unknown.supervisor.model.SysUser;
import com.unknown.supervisor.service.SysUserService;
import com.unknown.supervisor.vo.sys.UserPageInputVo;
import com.unknown.supervisor.vo.sys.UserPageOutputVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhongkunming
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public PageResult<UserPageOutputVo> page(UserPageInputVo input) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().orderByAsc(SysUser::getId);
        IPage<SysUser> page = sysUserMapper.selectPage(input.toPage(), wrapper);
        return PageResult.trans(page, UserPageOutputVo::new);
    }
}

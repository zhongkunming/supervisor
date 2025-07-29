package com.unknown.supervisor.portal.controller;

import com.unknown.supervisor.core.common.JsonResult;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.portal.dto.user.SysUserCreateInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserDTO;
import com.unknown.supervisor.portal.dto.user.SysUserQueryInputDTO;
import com.unknown.supervisor.portal.dto.user.SysUserUpdateInputDTO;
import com.unknown.supervisor.portal.service.SysUserService;
import com.unknown.supervisor.portal.vo.user.*;
import com.unknown.supervisor.utils.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户")
    @PostMapping("/query")
    public JsonResult<PageResult<SysUserVO>> queryUsers(@Valid @RequestBody SysUserQueryInputVO pageRequest) {
        // VO转DTO
        SysUserQueryInputDTO pageDTO = BeanUtils.copyProperties(pageRequest, SysUserQueryInputDTO::new);
        PageResult<SysUserDTO> dtoPage = sysUserService.pageUsers(pageDTO);

        // DTO转VO
        List<SysUserVO> voList = dtoPage.getRecords().stream()
                .map(dto -> {
                    SysUserVO vo = BeanUtils.copyProperties(dto, SysUserVO::new);
                    vo.setStatusName(dto.getStatus() == 1 ? "启用" : "禁用");
                    return vo;
                })
                .collect(Collectors.toList());

        PageResult<SysUserVO> pageResult = PageResult.trans(dtoPage, voList);
        return JsonResult.success(pageResult);
    }

    @Operation(summary = "创建用户")
    @PostMapping("/create")
    public JsonResult<Void> createUser(@Valid @RequestBody SysUserCreateInputVO createVO) {
        // VO转DTO
        SysUserCreateInputDTO createDTO = BeanUtils.copyProperties(createVO, SysUserCreateInputDTO::new);
        sysUserService.createUser(createDTO);
        return JsonResult.success();
    }

    @Operation(summary = "更新用户")
    @PostMapping("/update")
    public JsonResult<Void> updateUser(@Valid @RequestBody SysUserUpdateInputVO updateVO) {
        // VO转DTO
        SysUserUpdateInputDTO updateDTO = BeanUtils.copyProperties(updateVO, SysUserUpdateInputDTO::new);
        sysUserService.updateUser(updateDTO);
        return JsonResult.success();
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    public JsonResult<Void> deleteUser(@Valid @RequestBody SysUserDeleteInputVO deleteVO) {
        sysUserService.deleteUser(deleteVO.getId());
        return JsonResult.success();
    }

    @Operation(summary = "启用/禁用用户")
    @PostMapping("/status")
    public JsonResult<Void> updateUserStatus(@Valid @RequestBody SysUserStatusInputVO statusVO) {
        sysUserService.updateUserStatus(statusVO.getId(), statusVO.getStatus());
        return JsonResult.success();
    }
}
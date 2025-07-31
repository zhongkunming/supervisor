package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.config.SysConfigDTO;
import com.unknown.supervisor.portal.entity.SysConfig;
import com.unknown.supervisor.portal.mapper.SysConfigMapper;
import com.unknown.supervisor.portal.service.SysConfigService;
import com.unknown.supervisor.portal.vo.config.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 参数配置Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public PageResult<SysConfigVO> pageQuery(SysConfigQueryInputVO inputVO) {
        Page<SysConfig> page = inputVO.toPage();
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysConfig::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getKey()), SysConfig::getKey, inputVO.getKey())
                .like(StringUtils.isNotBlank(inputVO.getName()), SysConfig::getName, inputVO.getName())
                .like(StringUtils.isNotBlank(inputVO.getValue()), SysConfig::getValue, inputVO.getValue())
                .eq(StringUtils.isNotBlank(inputVO.getType()), SysConfig::getType, inputVO.getType())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysConfig::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysConfig::getUpdateDt, inputVO.getUpdateDt())
                .like(StringUtils.isNotBlank(inputVO.getRemark()), SysConfig::getRemark, inputVO.getRemark())
                .orderByDesc(SysConfig::getCreateDt);

        sysConfigMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysConfigVO getById(SysConfigGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysConfig config = sysConfigMapper.selectById(id);
        if (Objects.isNull(config)) {
            throw new BusinessException(PortalResultCode.CONFIG_NOT_FOUND);
        }
        return convertToVO(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createConfig(SysConfigCreateInputVO inputVO) {
        // 检查参数键名是否已存在
        String key = inputVO.getKey();
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getKey, key);
        if (sysConfigMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.CONFIG_ALREADY_EXISTS, key);
        }

        SysConfig sysConfig = BeanUtils.copyProperties(inputVO, SysConfig::new);
        sysConfigMapper.insert(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(SysConfigUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysConfig existConfig = sysConfigMapper.selectById(id);
        if (Objects.isNull(existConfig)) {
            throw new BusinessException(PortalResultCode.CONFIG_NOT_FOUND);
        }

        // 检查参数键名是否被其他记录使用
        String key = inputVO.getKey();
        if (!Objects.equals(key, existConfig.getKey())) {
            LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysConfig::getKey, key)
                    .ne(SysConfig::getId, id);
            if (sysConfigMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.CONFIG_ALREADY_EXISTS, key);
            }
        }

        SysConfig sysConfig = BeanUtils.copyProperties(inputVO, SysConfig::new);
        sysConfigMapper.updateById(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(SysConfigDeleteInputVO inputVO) {
        sysConfigMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysConfigVO convertToVO(SysConfig config) {
        if (Objects.isNull(config)) return null;
        return BeanUtils.copyProperties(config, SysConfigVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysConfigDTO convertToDTO(SysConfig config) {
        if (Objects.isNull(config)) return null;
        return BeanUtils.copyProperties(config, SysConfigDTO::new);
    }
}
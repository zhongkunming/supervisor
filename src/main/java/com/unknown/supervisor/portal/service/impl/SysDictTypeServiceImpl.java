package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.dicttype.SysDictTypeDTO;
import com.unknown.supervisor.portal.entity.SysDictData;
import com.unknown.supervisor.portal.entity.SysDictType;
import com.unknown.supervisor.portal.mapper.SysDictDataMapper;
import com.unknown.supervisor.portal.mapper.SysDictTypeMapper;
import com.unknown.supervisor.portal.service.SysDictTypeService;
import com.unknown.supervisor.portal.vo.dicttype.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 字典类型Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper sysDictDataMapper;

    @Override
    public PageResult<SysDictTypeVO> pageQuery(SysDictTypeQueryInputVO inputVO) {
        Page<SysDictType> page = inputVO.toPage();
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysDictType::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getCode()), SysDictType::getCode, inputVO.getCode())
                .like(StringUtils.isNotBlank(inputVO.getName()), SysDictType::getName, inputVO.getName())
                .like(StringUtils.isNotBlank(inputVO.getType()), SysDictType::getType, inputVO.getType())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysDictType::getStatus, inputVO.getStatus())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysDictType::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysDictType::getUpdateDt, inputVO.getUpdateDt())
                .like(StringUtils.isNotBlank(inputVO.getRemark()), SysDictType::getRemark, inputVO.getRemark())
                .orderByDesc(SysDictType::getCreateDt);

        sysDictTypeMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysDictTypeVO getById(SysDictTypeGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysDictType dictType = sysDictTypeMapper.selectById(id);
        if (Objects.isNull(dictType)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_NOT_FOUND);
        }
        return convertToVO(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDictType(SysDictTypeCreateInputVO inputVO) {
        // 检查字典类型是否已存在
        String type = inputVO.getType();
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictType::getType, type);
        if (sysDictTypeMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_ALREADY_EXISTS, type);
        }

        SysDictType sysDictType = BeanUtils.copyProperties(inputVO, SysDictType::new);
        sysDictTypeMapper.insert(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(SysDictTypeUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysDictType existDictType = sysDictTypeMapper.selectById(id);
        if (Objects.isNull(existDictType)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_NOT_FOUND);
        }

        // 检查字典类型是否被其他记录使用
        String type = inputVO.getType();
        if (!Objects.equals(type, existDictType.getType())) {
            LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictType::getType, type)
                    .ne(SysDictType::getId, id);
            if (sysDictTypeMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.DICT_TYPE_ALREADY_EXISTS, type);
            }
        }

        SysDictType sysDictType = BeanUtils.copyProperties(inputVO, SysDictType::new);
        sysDictTypeMapper.updateById(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(SysDictTypeDeleteInputVO inputVO) {
        for (Long id : inputVO.getIds()) {
            SysDictType dictType = sysDictTypeMapper.selectById(id);
            if (Objects.isNull(dictType)) {
                continue;
            }

            // 检查字典类型是否正在使用中
            LambdaQueryWrapper<SysDictData> dataWrapper = new LambdaQueryWrapper<>();
            dataWrapper.eq(SysDictData::getType, dictType.getType());
            if (sysDictDataMapper.exists(dataWrapper)) {
                throw new BusinessException(PortalResultCode.DICT_TYPE_IN_USE);
            }
        }

        sysDictTypeMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysDictTypeVO convertToVO(SysDictType dictType) {
        if (Objects.isNull(dictType)) return null;
        return BeanUtils.copyProperties(dictType, SysDictTypeVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysDictTypeDTO convertToDTO(SysDictType dictType) {
        if (Objects.isNull(dictType)) return null;
        return BeanUtils.copyProperties(dictType, SysDictTypeDTO::new);
    }
}
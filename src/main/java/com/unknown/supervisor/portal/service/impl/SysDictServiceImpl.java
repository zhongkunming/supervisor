package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.dict.SysDictTypeDTO;
import com.unknown.supervisor.portal.entity.SysDictData;
import com.unknown.supervisor.portal.entity.SysDictType;
import com.unknown.supervisor.portal.mapper.SysDictDataMapper;
import com.unknown.supervisor.portal.mapper.SysDictTypeMapper;
import com.unknown.supervisor.portal.service.SysDictService;
import com.unknown.supervisor.portal.vo.dict.*;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataQueryInputVO;
import com.unknown.supervisor.portal.vo.dictdata.SysDictDataVO;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 字典类型Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper sysDictDataMapper;
    private final CacheService cacheService;

    private LambdaQueryWrapper<SysDictType> newDictTypeListWrapper(String type, String name, String status, LocalDate createDtBegin, LocalDate createDtEnd) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(type), SysDictType::getType, type)
                .like(StringUtils.isNotBlank(name), SysDictType::getName, name)
                .eq(StringUtils.isNotBlank(status), SysDictType::getStatus, status)
                .between(ObjectUtils.allNotNull(createDtBegin, createDtEnd),
                        SysDictType::getCreateDt, createDtBegin, createDtEnd)
                .orderByDesc(SysDictType::getCreateDt)
                .orderByDesc(SysDictType::getUpdateDt);
        return wrapper;
    }

    @Override
    public PageResult<SysDictTypeVO> pageQueryDictType(SysDictTypeQueryInputVO inputVO) {
        Page<SysDictType> page = inputVO.toPage();
        LambdaQueryWrapper<SysDictType> wrapper = newDictTypeListWrapper(
                inputVO.getType(), inputVO.getName(), inputVO.getStatus(),
                inputVO.getCreateDtBegin(), inputVO.getCreateDtEnd());
        sysDictTypeMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysDictTypeVO getTypeById(SysDictTypeGetInputVO inputVO) {
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

        // 检查字典类型是否被其他字典使用
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

    @Override
    public void exportDictType(SysDictTypeExportInputVO inputVO) {
        LambdaQueryWrapper<SysDictType> wrapper = newDictTypeListWrapper(
                inputVO.getType(), inputVO.getName(), inputVO.getStatus(),
                inputVO.getCreateDtBegin(), inputVO.getCreateDtEnd());
        List<SysDictType> typeList = sysDictTypeMapper.selectList(wrapper);

    }

    @Override
    public void refreshCache() {
        cacheService.deleteByPattern(CacheModule.DICT, "*");
    }

    @Override
    public List<SysDictDataVO> getDictData(SysDictDateListInputVO inputVO) {
        String type = inputVO.getType();
        return cacheService.get(CacheModule.DICT, type, () -> {
            LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictData::getType, type)
                    .eq(SysDictData::getStatus, "0")
                    .orderByAsc(SysDictData::getOrderNum);
            List<SysDictData> dataList = sysDictDataMapper.selectList(wrapper);
            return dataList.stream().map(this::convertToVO).toList();
        }, Duration.ofDays(1));
    }

    @Override
    public List<SysDictTypeVO> getDictTypeListAll() {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDictType::getCreateDt);
        List<SysDictType> typeList = sysDictTypeMapper.selectList(wrapper);
        return typeList.stream().map(this::convertToVO).toList();
    }

    @Override
    public PageResult<SysDictDataVO> pageQueryDictData(SysDictDataQueryInputVO inputVO) {
        Page<SysDictData> page = inputVO.toPage();
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtils.isNotBlank(inputVO.getType()), SysDictData::getType, inputVO.getType())
                .like(StringUtils.isNotBlank(inputVO.getLabel()), SysDictData::getLabel, inputVO.getLabel())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysDictData::getStatus, inputVO.getStatus())
                .orderByAsc(SysDictData::getOrderNum)
                .orderByDesc(SysDictData::getCreateDt);

        sysDictDataMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
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

    /**
     * 转换实体类到VO
     */
    private SysDictDataVO convertToVO(SysDictData dictData) {
        if (Objects.isNull(dictData)) return null;
        return BeanUtils.copyProperties(dictData, SysDictDataVO::new);
    }
}
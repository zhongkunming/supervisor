package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.dictdata.SysDictDataDTO;
import com.unknown.supervisor.portal.entity.SysDictData;
import com.unknown.supervisor.portal.entity.SysDictType;
import com.unknown.supervisor.portal.mapper.SysDictDataMapper;
import com.unknown.supervisor.portal.mapper.SysDictTypeMapper;
import com.unknown.supervisor.portal.service.SysDictDataService;
import com.unknown.supervisor.portal.vo.dictdata.*;
import com.unknown.supervisor.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 字典数据Service实现类
 *
 * @author zhongkunming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;
    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public PageResult<SysDictDataVO> pageQuery(SysDictDataQueryInputVO inputVO) {
        Page<SysDictData> page = inputVO.toPage();
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        wrapper.eq(Objects.nonNull(inputVO.getId()), SysDictData::getId, inputVO.getId())
                .like(StringUtils.isNotBlank(inputVO.getDictCode()), SysDictData::getDictCode, inputVO.getDictCode())
                .eq(Objects.nonNull(inputVO.getOrderNum()), SysDictData::getOrderNum, inputVO.getOrderNum())
                .like(StringUtils.isNotBlank(inputVO.getLabel()), SysDictData::getLabel, inputVO.getLabel())
                .like(StringUtils.isNotBlank(inputVO.getValue()), SysDictData::getValue, inputVO.getValue())
                .eq(StringUtils.isNotBlank(inputVO.getType()), SysDictData::getType, inputVO.getType())
                .like(StringUtils.isNotBlank(inputVO.getCssClass()), SysDictData::getCssClass, inputVO.getCssClass())
                .like(StringUtils.isNotBlank(inputVO.getListClass()), SysDictData::getListClass, inputVO.getListClass())
                .eq(Objects.nonNull(inputVO.getIsDefault()), SysDictData::getIsDefault, inputVO.getIsDefault())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysDictData::getStatus, inputVO.getStatus())
                .ge(Objects.nonNull(inputVO.getCreateDt()), SysDictData::getCreateDt, inputVO.getCreateDt())
                .le(Objects.nonNull(inputVO.getUpdateDt()), SysDictData::getUpdateDt, inputVO.getUpdateDt())
                .like(StringUtils.isNotBlank(inputVO.getRemark()), SysDictData::getRemark, inputVO.getRemark())
                .orderByAsc(SysDictData::getOrderNum)
                .orderByDesc(SysDictData::getCreateDt);

        sysDictDataMapper.selectPage(page, wrapper);
        return PageResult.trans(page, this::convertToVO);
    }

    @Override
    public SysDictDataVO getById(SysDictDataGetInputVO inputVO) {
        Long id = inputVO.getId();
        SysDictData dictData = sysDictDataMapper.selectById(id);
        if (Objects.isNull(dictData)) {
            throw new BusinessException(PortalResultCode.DICT_DATA_NOT_FOUND);
        }
        return convertToVO(dictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDictData(SysDictDataCreateInputVO inputVO) {
        // 检查字典类型是否存在
        String type = inputVO.getType();
        LambdaQueryWrapper<SysDictType> typeWrapper = new LambdaQueryWrapper<>();
        typeWrapper.eq(SysDictType::getType, type);
        if (!sysDictTypeMapper.exists(typeWrapper)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_NOT_FOUND);
        }

        // 检查字典数据是否已存在（同一类型下标签或键值不能重复）
        String label = inputVO.getLabel();
        String value = inputVO.getValue();
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getType, type)
                .and(w -> w.eq(SysDictData::getLabel, label).or().eq(SysDictData::getValue, value));
        if (sysDictDataMapper.exists(wrapper)) {
            throw new BusinessException(PortalResultCode.DICT_DATA_ALREADY_EXISTS, label + "/" + value);
        }

        SysDictData sysDictData = BeanUtils.copyProperties(inputVO, SysDictData::new);
        sysDictDataMapper.insert(sysDictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictData(SysDictDataUpdateInputVO inputVO) {
        Long id = inputVO.getId();
        SysDictData existDictData = sysDictDataMapper.selectById(id);
        if (Objects.isNull(existDictData)) {
            throw new BusinessException(PortalResultCode.DICT_DATA_NOT_FOUND);
        }

        // 检查字典类型是否存在
        String type = inputVO.getType();
        LambdaQueryWrapper<SysDictType> typeWrapper = new LambdaQueryWrapper<>();
        typeWrapper.eq(SysDictType::getType, type);
        if (!sysDictTypeMapper.exists(typeWrapper)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_NOT_FOUND);
        }

        // 检查字典数据是否被其他记录使用（同一类型下标签或键值不能重复）
        String label = inputVO.getLabel();
        String value = inputVO.getValue();
        if (!Objects.equals(type, existDictData.getType()) ||
                !Objects.equals(label, existDictData.getLabel()) ||
                !Objects.equals(value, existDictData.getValue())) {
            LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictData::getType, type)
                    .and(w -> w.eq(SysDictData::getLabel, label).or().eq(SysDictData::getValue, value))
                    .ne(SysDictData::getId, id);
            if (sysDictDataMapper.exists(wrapper)) {
                throw new BusinessException(PortalResultCode.DICT_DATA_ALREADY_EXISTS, label + "/" + value);
            }
        }

        SysDictData sysDictData = BeanUtils.copyProperties(inputVO, SysDictData::new);
        sysDictDataMapper.updateById(sysDictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(SysDictDataDeleteInputVO inputVO) {
        sysDictDataMapper.deleteByIds(inputVO.getIds());
    }

    /**
     * 转换实体类到VO
     */
    private SysDictDataVO convertToVO(SysDictData dictData) {
        if (Objects.isNull(dictData)) return null;
        return BeanUtils.copyProperties(dictData, SysDictDataVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysDictDataDTO convertToDTO(SysDictData dictData) {
        if (Objects.isNull(dictData)) return null;
        return BeanUtils.copyProperties(dictData, SysDictDataDTO::new);
    }
}
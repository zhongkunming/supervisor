package com.unknown.supervisor.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.common.PageResult;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.portal.common.PortalResultCode;
import com.unknown.supervisor.portal.dto.dict.SysDictDataDTO;
import com.unknown.supervisor.portal.dto.dict.SysDictTypeDTO;
import com.unknown.supervisor.portal.entity.SysDictData;
import com.unknown.supervisor.portal.entity.SysDictType;
import com.unknown.supervisor.portal.mapper.SysDictDataMapper;
import com.unknown.supervisor.portal.mapper.SysDictTypeMapper;
import com.unknown.supervisor.portal.service.SysDictService;
import com.unknown.supervisor.portal.vo.dict.*;
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

import static org.apache.commons.lang3.Strings.CS;

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
    public SysDictTypeVO getDictTypeById(SysDictTypeGetInputVO inputVO) {
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
        cacheService.delete(CacheModule.DICT, sysDictType.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(SysDictTypeDeleteInputVO inputVO) {
        List<SysDictType> dictTypeList = sysDictTypeMapper.selectByIds(inputVO.getIds());
        List<String> typeList = dictTypeList.stream().map(SysDictType::getType).distinct().toList();

        // 检查字典类型是否正在使用中
        LambdaQueryWrapper<SysDictData> dataWrapper = new LambdaQueryWrapper<>();
        dataWrapper.in(SysDictData::getType, typeList);
        if (sysDictDataMapper.exists(dataWrapper)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_IN_USE);
        }

        sysDictTypeMapper.deleteByIds(inputVO.getIds());
        cacheService.delete(CacheModule.DICT, typeList);
    }

    @Override
    public void exportDictType(SysDictTypeExportInputVO inputVO) {
        LambdaQueryWrapper<SysDictType> wrapper = newDictTypeListWrapper(
                inputVO.getType(), inputVO.getName(), inputVO.getStatus(),
                inputVO.getCreateDtBegin(), inputVO.getCreateDtEnd());
        List<SysDictType> dictTypeList = sysDictTypeMapper.selectList(wrapper);
        List<SysDictTypeDTO> dtoList = dictTypeList.stream().map(this::convertToDTO).toList();
//        File file = new File("dict.xlsx");
//        FastExcel.write(file, SysDictTypeDTO.class)
//                .sheet()
//                .doWrite(dtoList);
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

    @Override
    public SysDictDataVO getDictDataById(SysDictDataGetInputVO inputVO) {
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
        checkDictTypeExists(type);

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
        cacheService.delete(CacheModule.DICT, type);
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
        checkDictTypeExists(type);

        // 检查字典数据是否被其他记录使用（同一类型下标签或键值不能重复）
        String label = inputVO.getLabel();
        String value = inputVO.getValue();
        if (!CS.equals(type, existDictData.getType()) ||
                !CS.equals(label, existDictData.getLabel()) ||
                !CS.equals(value, existDictData.getValue())) {
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
        cacheService.delete(CacheModule.DICT, type);
    }

    private void checkDictTypeExists(String type) {
        LambdaQueryWrapper<SysDictType> typeWrapper = new LambdaQueryWrapper<>();
        typeWrapper.eq(SysDictType::getType, type);
        if (sysDictTypeMapper.exists(typeWrapper)) {
            throw new BusinessException(PortalResultCode.DICT_TYPE_NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(SysDictDataDeleteInputVO inputVO) {
        List<SysDictData> dictDataList = sysDictDataMapper.selectByIds(inputVO.getIds());
        List<String> typeList = dictDataList.stream().map(SysDictData::getType).distinct().toList();
        sysDictDataMapper.deleteByIds(inputVO.getIds());
        cacheService.delete(CacheModule.DICT, typeList);
    }

    @Override
    public void exportDictData(SysDictDataExportInputVO inputVO) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(inputVO.getType()), SysDictData::getType, inputVO.getType())
                .like(StringUtils.isNotBlank(inputVO.getLabel()), SysDictData::getLabel, inputVO.getLabel())
                .eq(StringUtils.isNotBlank(inputVO.getStatus()), SysDictData::getStatus, inputVO.getStatus())
                .orderByAsc(SysDictData::getOrderNum)
                .orderByDesc(SysDictData::getCreateDt);
        List<SysDictData> dictDataList = sysDictDataMapper.selectList(wrapper);

    }

    /**
     * 转换实体类到VO
     */
    private SysDictTypeVO convertToVO(SysDictType entity) {
        if (Objects.isNull(entity)) return null;
        return BeanUtils.copyProperties(entity, SysDictTypeVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysDictTypeDTO convertToDTO(SysDictType entity) {
        if (Objects.isNull(entity)) return null;
        return BeanUtils.copyProperties(entity, SysDictTypeDTO::new);
    }

    /**
     * 转换实体类到VO
     */
    private SysDictDataVO convertToVO(SysDictData entity) {
        if (Objects.isNull(entity)) return null;
        return BeanUtils.copyProperties(entity, SysDictDataVO::new);
    }

    /**
     * 转换实体类到DTO
     */
    private SysDictDataDTO convertToDTO(SysDictData entity) {
        if (Objects.isNull(entity)) return null;
        return BeanUtils.copyProperties(entity, SysDictDataDTO::new);
    }
}
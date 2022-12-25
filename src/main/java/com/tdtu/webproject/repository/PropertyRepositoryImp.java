package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtPropertyExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtPropertyMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtProperty;
import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.mapper.PropertySupportMapper;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyRepositoryImp implements PropertyRepository {
    private final PropertySupportMapper propertySupportMapper;
    private final TdtPropertyMapper tdtPropertyMapper;

    @Override
    public Long countProperty(PropertySearchCondition condition) {
        return propertySupportMapper.count(condition);
    }

    @Override
    public List<PropertySearchResult> searchProperty(PropertySearchCondition condition) {
        return propertySupportMapper.select(condition);
    }

    @Override
    public List<TdtProperty> selectAll() {
        TdtPropertyExample example = new TdtPropertyExample();
        TdtPropertyExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyMapper.selectByExample(example);
    }

    @Override
    public int create(TdtProperty record) {
        return tdtPropertyMapper.insertSelective(record);
    }

    @Override
    public int update(TdtProperty record, BigDecimal propertyId) {
        TdtPropertyExample example = new TdtPropertyExample();
        TdtPropertyExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(BigDecimal propertyId) {
        TdtPropertyExample example = new TdtPropertyExample();
        TdtPropertyExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyMapper.updateByExampleSelective(
                TdtProperty.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }
}

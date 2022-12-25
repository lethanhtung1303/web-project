package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtPropertyInfoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtPropertyInfoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyInfo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyInfoRepositoryImp implements PropertyInfoRepository {
    private final TdtPropertyInfoMapper tdtPropertyInfoMapper;
    @Override
    public int create(TdtPropertyInfo record) {
        return tdtPropertyInfoMapper.insertSelective(record);
    }

    @Override
    public int update(TdtPropertyInfo record, BigDecimal propertyId) {
        TdtPropertyInfoExample example = new TdtPropertyInfoExample();
        TdtPropertyInfoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(BigDecimal propertyId) {
        TdtPropertyInfoExample example = new TdtPropertyInfoExample();
        TdtPropertyInfoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyInfoMapper.updateByExampleSelective(
                TdtPropertyInfo.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }
}


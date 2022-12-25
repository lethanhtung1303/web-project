package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtProperty;
import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepository {

    Long countProperty(PropertySearchCondition condition);
    List<PropertySearchResult> searchProperty(PropertySearchCondition condition);
    List<TdtProperty> selectAll();

    int create(TdtProperty record);

    int update(TdtProperty record, BigDecimal propertyId);

    int delete(BigDecimal propertyId);
}


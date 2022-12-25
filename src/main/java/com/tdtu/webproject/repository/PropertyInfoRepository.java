package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyInfo;

import java.math.BigDecimal;

public interface PropertyInfoRepository {
    int create(TdtPropertyInfo record);

    int update(TdtPropertyInfo record, BigDecimal propertyId);

    int delete(BigDecimal propertyId);
}


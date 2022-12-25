package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyImgRepository {

    List<TdtPropertyImg> selectAll(BigDecimal propertyId);

    int create(TdtPropertyImg record);

    int update(TdtPropertyImg record, BigDecimal propertyId);

    int delete(BigDecimal propertyId);
}


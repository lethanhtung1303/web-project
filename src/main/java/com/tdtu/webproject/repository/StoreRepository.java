package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtStorage;

import java.math.BigDecimal;
import java.util.List;

public interface StoreRepository {

    Long count(BigDecimal userId);

    List<TdtStorage> search(BigDecimal userId);
    List<TdtStorage> selectAll();
    int addStore(TdtStorage record);
    int update(TdtStorage record);
}


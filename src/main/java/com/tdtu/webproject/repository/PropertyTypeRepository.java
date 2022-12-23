package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;

import java.util.List;

public interface PropertyTypeRepository {

    List<TdtPropertyType> selectAll();
    Long countAll();
}


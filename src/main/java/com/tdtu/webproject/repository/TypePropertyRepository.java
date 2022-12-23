package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;

import java.util.List;

public interface TypePropertyRepository {

    List<TdtPropertyType> selectAll();
}


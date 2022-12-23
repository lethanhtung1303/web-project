package com.tdtu.webproject.repository;

import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;

import java.util.List;

public interface PropertyRepository {

    Long countUser(PropertySearchCondition condition);
    List<PropertySearchResult> searchProperty(PropertySearchCondition condition);
}


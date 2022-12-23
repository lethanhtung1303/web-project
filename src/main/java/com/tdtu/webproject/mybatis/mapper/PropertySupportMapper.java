package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertySupportMapper {
    List<PropertySearchResult> select(PropertySearchCondition condition);
    Long count(PropertySearchCondition condition);
}
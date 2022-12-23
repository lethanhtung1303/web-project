package com.tdtu.webproject.repository;

import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.mapper.PropertySupportMapper;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyRepositoryImp implements PropertyRepository {
    private final PropertySupportMapper propertySupportMapper;

    @Override
    public Long countUser(PropertySearchCondition condition) {
        return propertySupportMapper.count(condition);
    }

    @Override
    public List<PropertySearchResult> searchProperty(PropertySearchCondition condition) {
        return propertySupportMapper.select(condition);
    }
}

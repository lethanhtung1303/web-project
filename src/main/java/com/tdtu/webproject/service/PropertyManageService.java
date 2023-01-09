package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtProperty;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;
import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import com.tdtu.webproject.repository.PropertyRepository;
import com.tdtu.webproject.repository.PropertyTypeRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyManageService {

    private final PropertyRepository propertyRepository;
    private final PropertyTypeRepository typePropertyRepository;

    public boolean checkExistProperty(BigDecimal propertyId){
        List<TdtProperty> results = this.getAllProperty().stream()
                .filter(property -> property.getPropertyId().equals(propertyId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public boolean checkExistPropertyType(BigDecimal propertyTypeId){
        List<TdtPropertyType> results = this.getAllPropertyType().stream()
                .filter(property -> property.getTypeId().equals(propertyTypeId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public List<TdtProperty> getAllProperty(){
        return propertyRepository.selectAll();
    }

    public List<TdtPropertyType> getAllPropertyType(){
        return typePropertyRepository.selectAll();
    }

    public List<PropertySearchResult> getAllPropertySearchResult(){
        return propertyRepository.searchProperty(PropertySearchCondition.builder().build());
    }

    public PropertySearchResult getPropertySearchResultById(BigDecimal propertyId){
        return this.getAllPropertySearchResult().stream()
                .filter(item -> item.getPropertyId().equals(propertyId))
                .findFirst()
                .orElse(null);
    }
}

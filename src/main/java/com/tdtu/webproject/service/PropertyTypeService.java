package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;
import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.model.PropertyTypeResponse;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import com.tdtu.webproject.repository.PropertyRepository;
import com.tdtu.webproject.repository.PropertyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyTypeService {
    private final PropertyTypeRepository typePropertyRepository;
    private final PropertyRepository propertyRepository;

    public Long countAllTypeProperty() {
        return typePropertyRepository.countAll();
    }

    public List<PropertyTypeResponse> getAllTypeProperty() {
        List<TdtPropertyType> propertyTypeResultList = typePropertyRepository.selectAll();
        return Optional.ofNullable(propertyTypeResultList).isPresent()
                ? propertyTypeResultList.stream()
                .map(result -> PropertyTypeResponse.builder()
                        .nameProperty(result.getName())
                        .totalProperty(this.totalProperty(result.getTypeId()))
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private Long totalProperty(BigDecimal typeId){
        List<PropertySearchResult> propertySearchResultList =
                propertyRepository.searchProperty(PropertySearchCondition.builder().build());
        return Optional.ofNullable(propertySearchResultList).isPresent()
                ? propertySearchResultList.stream()
                .filter(property -> property.getTypeId().equals(typeId))
                .count()
                : 0L;
    }
}

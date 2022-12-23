package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.PropertyTypeService;
import com.tdtu.webproject.usecase.PropertyTypeUseCaseOutput.PropertyTypeUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyTypeGetAllUseCase {
    private final PropertyTypeService typePropertyService;
    public PropertyTypeUseCaseOutput getAllTypeProperty() {
        return PropertyTypeUseCaseOutput.builder()
                .resultsTotalCount(typePropertyService.countAllTypeProperty())
                .typeProperties(typePropertyService.getAllTypeProperty().stream()
                        .map(response -> PropertyTypeUseCaseResults.builder()
                                .nameProperty(response.getNameProperty())
                                .totalProperty(response.getTotalProperty())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

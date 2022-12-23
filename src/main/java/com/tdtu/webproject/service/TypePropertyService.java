package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;
import com.tdtu.webproject.repository.TypePropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TypePropertyService {
    private final TypePropertyRepository typePropertyRepository;
    public List<String> getAllTypeProperty() {
        List<TdtPropertyType> response = typePropertyRepository.selectAll();
        return Optional.ofNullable(response).isPresent()
                ? response.stream()
                .map(TdtPropertyType::getName)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.TypePropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class TypePropertyGetAllUseCase {
    private final TypePropertyService typePropertyService;
    public List<String> getAllTypeProperty() {
        return typePropertyService.getAllTypeProperty();
    }
}

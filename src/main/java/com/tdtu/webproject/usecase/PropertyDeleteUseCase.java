package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyDeleteUseCase {
    private final PropertyService propertyService;
    public String deleteProperty(String propertyId) {
        return propertyService.deleteProperty(propertyId);
    }
}

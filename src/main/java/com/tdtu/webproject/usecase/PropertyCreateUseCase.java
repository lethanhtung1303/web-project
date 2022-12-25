package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.PropertyCreateRequest;
import com.tdtu.webproject.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyCreateUseCase {

    private final PropertyService propertyService;

    public String createProperty(PropertyCreateUseCaseInput input) {
        PropertyCreateRequest request = this.buildPropertyCreateRequest(input);
        return propertyService.createProperty(request);
    }

    private PropertyCreateRequest buildPropertyCreateRequest(PropertyCreateUseCaseInput input) {
        return PropertyCreateRequest.builder()
                .typeId(input.getTypeId())
                .createUserId(input.getCreateUserId())
                .title(input.getTitle())
                .address(input.getAddress())
                .amount(input.getAmount())
                .area(input.getArea())
                .description(input.getDescription())
                .propertyImg(input.getPropertyImg())
                .build();
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.PropertyUpdateRequest;
import com.tdtu.webproject.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyUpdateUseCase {

    private final PropertyService propertyService;
    public String updateProperty(PropertyUpdateUseCaseInput input) {
        PropertyUpdateRequest request = this.buildUserSearchRequest(input);
        return propertyService.updateProperty(request);
    }

    private PropertyUpdateRequest buildUserSearchRequest(PropertyUpdateUseCaseInput input) {
        return PropertyUpdateRequest.builder()
                .propertyId(input.getPropertyId())
                .typeId(input.getTypeId())
                .lastupUserId(input.getLastupUserId())
                .title(input.getTitle())
                .address(input.getAddress())
                .amount(input.getAmount())
                .area(input.getArea())
                .description(input.getDescription())
                .propertyImg(input.getPropertyImg())
                .build();
    }
}

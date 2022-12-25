package com.tdtu.webproject.usecase;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.PropertySearchRequest;
import com.tdtu.webproject.model.PropertySearchResponse;
import com.tdtu.webproject.service.PropertyService;
import com.tdtu.webproject.usecase.PropertySearchUseCaseOutput.PropertySearchUseCaseResults;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyGetUseCase {

    private final PropertyService propertyService;

    public PropertySearchUseCaseResults getProperty(String propertyId) {
        List<PropertySearchResponse> propertyList = propertyService.search(PropertySearchRequest.builder()
                .propertyId(propertyId)
                .build());
        if (!ArrayUtil.isNotNullOrEmptyList(propertyList)){
            throw new BusinessException("Not found property!");
        }
        return propertyList.stream()
                .filter(user -> user.getPropertyId().equals(propertyId))
                .findFirst()
                .map(property -> PropertySearchUseCaseResults.builder()
                        .propertyId(property.getPropertyId())
                        .typeId(property.getTypeId())
                        .typeName(property.getTypeName())
                        .createUserId(property.getCreateUserId())
                        .createUserName(property.getCreateUserName())
                        .createDatetime(property.getCreateDatetime())
                        .lastupUserId(property.getLastupUserId())
                        .lastupUserName(property.getLastupUserName())
                        .lastupDatetime(property.getLastupDatetime())
                        .title(property.getTitle())
                        .address(property.getAddress())
                        .amount(property.getAmount())
                        .area(property.getArea())
                        .description(property.getDescription())
                        .approveStatus(property.getApproveStatus())
                        .propertyImg(property.getPropertyImg())
                        .build())
                .orElse(null);
    }
}

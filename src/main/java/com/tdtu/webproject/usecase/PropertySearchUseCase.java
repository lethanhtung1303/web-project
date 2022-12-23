package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.PropertySearchRequest;
import com.tdtu.webproject.model.PropertySearchResponse;
import com.tdtu.webproject.service.PropertyService;
import com.tdtu.webproject.usecase.PropertySearchUseCaseOutput.PropertySearchUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertySearchUseCase {

    private final PropertyService propertyService;

    public PropertySearchUseCaseOutput searchProperty(PropertySearchUseCaseInput input) {
        PropertySearchRequest request = this.buildPropertySearchRequest(input);
        return PropertySearchUseCaseOutput.builder()
                .resultsTotalCount(propertyService.count(request))
                .properties(propertyService.search(request).stream()
                        .map(this::buildPropertySearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private PropertySearchRequest buildPropertySearchRequest(PropertySearchUseCaseInput input) {
        return PropertySearchRequest.builder()
                .userId(input.getUserId())
                .typeId(input.getTypeId())
                .title(input.getTitle())
                .address(input.getAddress())
                .createDatetimeFrom(input.getCreateDatetimeFrom())
                .createDatetimeTo(input.getCreateDatetimeTo())
                .amountFrom(input.getAmountFrom())
                .amountTo(input.getAmountTo())
                .areaFrom(input.getAreaFrom())
                .areaTo(input.getAreaTo())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .build();
    }

    private PropertySearchUseCaseResults buildPropertySearchUseCaseResults(PropertySearchResponse response) {
        return PropertySearchUseCaseResults.builder()
                .propertyId(response.getPropertyId())
                .typeId(response.getTypeId())
                .typeName(response.getTypeName())
                .createUserId(response.getCreateUserId())
                .createUserName(response.getCreateUserName())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupUserName(response.getLastupUserName())
                .lastupDatetime(response.getLastupDatetime())
                .title(response.getTitle())
                .address(response.getAddress())
                .amount(response.getAmount())
                .area(response.getArea())
                .description(response.getDescription())
                .approveStatus(response.getApproveStatus())
                .propertyImg(response.getPropertyImg())
                .build();
    }
}

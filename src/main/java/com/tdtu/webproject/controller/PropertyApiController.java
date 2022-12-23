package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import com.tdtu.webproject.usecase.PropertySearchUseCaseOutput.PropertySearchUseCaseResults;
import generater.openapi.api.PropertyApi;
import generater.openapi.model.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class PropertyApiController implements PropertyApi {

    private final PropertyTypeGetAllUseCase typePropertyGetAllUseCase;
    private final PropertySearchUseCase propertySearchUseCase;

    @Override
    public ResponseEntity<AllPropertyTypeResponse> getAllPropertyType() {

        PropertyTypeUseCaseOutput output = typePropertyGetAllUseCase.getAllTypeProperty();
        return ResponseEntity.ok(AllPropertyTypeResponse.builder()
                .status(HttpStatus.OK.value())
                .results(AllPropertyTypeResponseResults.builder()
                        .typeProperties(output.getTypeProperties().stream()
                                .map(typeProperty -> PropertyTypeResponse.builder()
                                        .nameProperty(typeProperty.getNameProperty())
                                        .totalProperty(typeProperty.getTotalProperty())
                                        .build())
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<SearchPropertyResponse> searchProperty(
            @ApiParam(value = "")
            @Valid
            @RequestBody SearchPropertyRequest searchPropertyRequest,
            BindingResult bindingResult1) {
        PropertySearchUseCaseOutput output =
                propertySearchUseCase.searchProperty(this.buildUserSearchUseCaseInput(searchPropertyRequest));
        return ResponseEntity.ok(SearchPropertyResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SearchPropertyResponseResults.builder()
                        .properties(output.getProperties().stream()
                                .map(this::buildUserResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private PropertySearchUseCaseInput buildUserSearchUseCaseInput(SearchPropertyRequest request) {
        return PropertySearchUseCaseInput.builder()
                .userId(request.getUserId())
                .typeId(request.getTypeId())
                .title(request.getTitle())
                .address(request.getAddress())
                .createDatetimeFrom(request.getCreateDatetimeFrom())
                .createDatetimeTo(request.getCreateDatetimeTo())
                .amountFrom(request.getAmountFrom())
                .amountTo(request.getAmountTo())
                .areaFrom(request.getAreaFrom())
                .areaTo(request.getAreaTo())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private PropertyResponse buildUserResponse(PropertySearchUseCaseResults results) {
        return PropertyResponse.builder()
                .propertyId(results.getPropertyId())
                .typeId(results.getTypeId())
                .typeName(results.getTypeName())
                .createUserId(results.getCreateUserId())
                .createUserName(results.getCreateUserName())
                .createDatetime(results.getCreateDatetime())
                .lastupUserId(results.getLastupUserId())
                .lastupUserName(results.getLastupUserName())
                .lastupDatetime(results.getLastupDatetime())
                .title(results.getTitle())
                .address(results.getAddress())
                .amount(results.getAmount())
                .area(results.getArea())
                .description(results.getDescription())
                .approveStatus(results.getApproveStatus())
                .propertyImg(results.getPropertyImg())
                .build();
    }
}

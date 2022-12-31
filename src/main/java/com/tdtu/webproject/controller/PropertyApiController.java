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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class PropertyApiController implements PropertyApi {

    private final PropertyTypeGetAllUseCase typePropertyGetAllUseCase;
    private final PropertySearchUseCase propertySearchUseCase;
    private final PropertyCreateUseCase propertyCreateUseCase;
    private final PropertyUpdateUseCase propertyUpdateUseCase;
    private final PropertyDeleteUseCase propertyDeleteUseCase;
    private final PropertyGetUseCase propertyGetUseCase;

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
                                .map(this::buildPropertyResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private PropertySearchUseCaseInput buildUserSearchUseCaseInput(SearchPropertyRequest request) {
        return PropertySearchUseCaseInput.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
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

    private PropertyResponse buildPropertyResponse(PropertySearchUseCaseResults results) {
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

    @Override
    public ResponseEntity<PropertyCreateResponse> createProperty(
            @ApiParam(value = "")
            @Valid
            @RequestBody PropertyCreateRequest propertyCreateRequest,
            BindingResult bindingResult1) {
        PropertyCreateUseCaseInput input = this.buildPropertyCreateUseCaseInput(propertyCreateRequest);
        return ResponseEntity.ok(PropertyCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(propertyCreateUseCase.createProperty(input))
                .build());
    }

    private PropertyCreateUseCaseInput buildPropertyCreateUseCaseInput(PropertyCreateRequest request) {
        return PropertyCreateUseCaseInput.builder()
                .typeId(request.getTypeId())
                .createUserId(request.getCreateUserId())
                .title(request.getTitle())
                .address(request.getAddress())
                .amount(request.getAmount())
                .area(request.getArea())
                .description(request.getDescription())
                .propertyImg(request.getPropertyImg())
                .build();
    }

    @Override
    public ResponseEntity<PropertyUpdateResponse> updateProperty(
            @ApiParam(value = "")
            @Valid
            @RequestBody PropertyUpdateRequest propertyUpdateRequest,
            BindingResult bindingResult1) {
        PropertyUpdateUseCaseInput input = this.buildPropertyUpdateUseCaseInput(propertyUpdateRequest);
        return ResponseEntity.ok(PropertyUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(propertyUpdateUseCase.updateProperty(input))
                .build());
    }

    private PropertyUpdateUseCaseInput buildPropertyUpdateUseCaseInput(PropertyUpdateRequest request) {
        return PropertyUpdateUseCaseInput.builder()
                .propertyId(request.getPropertyId())
                .typeId(request.getTypeId())
                .lastupUserId(request.getLastupUserId())
                .title(request.getTitle())
                .address(request.getAddress())
                .amount(request.getAmount())
                .area(request.getArea())
                .description(request.getDescription())
                .propertyImg(request.getPropertyImg())
                .build();
    }

    @Override
    public ResponseEntity<PropertyDeleteResponse> deleteProperty(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "propertyId", required = true)
            String propertyId ) {
        return ResponseEntity.ok(PropertyDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(propertyDeleteUseCase.deleteProperty(propertyId))
                .build());
    }

    @Override
    public ResponseEntity<PropertyGetResponse> getProperty(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "propertyId", required = true)
            String propertyId ) {
        PropertySearchUseCaseResults output = propertyGetUseCase.getProperty(propertyId);
        return ResponseEntity.ok(PropertyGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(Optional.ofNullable(output).isPresent()
                        ? List.of(this.buildPropertyResponse(output))
                        : Collections.emptyList())
                .build());
    }
}

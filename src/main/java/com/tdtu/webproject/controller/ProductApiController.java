package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import generater.openapi.api.ProductApi;
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
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class ProductApiController implements ProductApi {

    private final ProductGetUseCase productGetUseCase;
    private final ProductSearchUseCase productSearchUseCase;

    @Override
    public ResponseEntity<ProductGetResponse> getProduct(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "productId", required = true)
            String productId) {
        ProductGetUseCaseOutput output = productGetUseCase.getProduct(productId);
        return ResponseEntity.ok(ProductGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(List.of(this.buildProductResponse(output)))
                .build());
    }

    private ProductResponse buildProductResponse(ProductGetUseCaseOutput output) {
        return ProductResponse.builder()
                .productId(output.getProductId())
                .productName(output.getProductName())
                .approveStatus(output.getApproveStatus())
                .amount(output.getAmount())
                .quantity(output.getQuantity())
                .createUserId(output.getCreateUserId())
                .createDatetime(output.getCreateDatetime())
                .lastupUserId(output.getLastupUserId())
                .lastupDatetime(output.getLastupDatetime())
                .isDelete(output.getIsDelete())
                .build();
    }

    @Override
    public ResponseEntity<ProductsSearchResponse> searchProduct(
            @ApiParam(value = "")
            @Valid
            @RequestBody ProductSearchRequest productSearchRequest,
            BindingResult bindingResult1) {
        ProductSearchUseCaseOutput output =
                productSearchUseCase.searchProduct(this.buildProductSearchUseCaseInput(productSearchRequest));
        return ResponseEntity.ok(ProductsSearchResponse.builder()
                .status(HttpStatus.OK.value())
                .results(ProductsSearchResponseResults.builder()
                        .products(output.getProducts().stream()
                                .map(this::buildProductResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private ProductSearchUseCaseInput buildProductSearchUseCaseInput(ProductSearchRequest request) {
        return ProductSearchUseCaseInput.builder()
                .productId(request.getProductId())
                .productName((request.getProductName()))
                .createDatetimeFrom(request.getCreateDatetimeFrom())
                .createDatetimeTo(request.getCreateDatetimeTo())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private ProductResponse buildProductResponse(ProductSearchUseCaseOutput.ProductSearchUseCaseResults results) {
        return ProductResponse.builder()
                .productId(results.getProductId())
                .productName(results.getProductName())
                .approveStatus(results.getApproveStatus())
                .amount(results.getAmount())
                .quantity(results.getQuantity())
                .createUserId(results.getCreateUserId())
                .createDatetime(results.getCreateDatetime())
                .lastupUserId(results.getLastupUserId())
                .lastupDatetime(results.getLastupDatetime())
                .isDelete(results.getIsDelete())
                .build();
    }
}

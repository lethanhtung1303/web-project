package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.ProductSearchRequest;
import com.tdtu.webproject.model.ProductSearchResponse;
import com.tdtu.webproject.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchUseCase {

    private final ProductService productService;

    public ProductSearchUseCaseOutput searchProduct(ProductSearchUseCaseInput input) {
        ProductSearchRequest request = this.buildProductSearchRequest(input);
        return ProductSearchUseCaseOutput.builder()
                .resultsTotalCount(productService.count(request))
                .products(productService.search(request).stream()
                        .map(this::buildProductSearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private ProductSearchRequest buildProductSearchRequest(ProductSearchUseCaseInput input) {
        return ProductSearchRequest.builder()
                .productId(input.getProductId())
                .productName(input.getProductName())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .createDatetimeFrom(input.getCreateDatetimeFrom())
                .createDatetimeTo(input.getCreateDatetimeTo())
                .build();
    }

    private ProductSearchUseCaseOutput.ProductSearchUseCaseResults buildProductSearchUseCaseResults(ProductSearchResponse response) {
        return ProductSearchUseCaseOutput.ProductSearchUseCaseResults.builder()
                .productId(response.getProductId())
                .productName(response.getProductName())
                .approveStatus(response.getApproveStatus())
                .amount(response.getAmount())
                .quantity(response.getQuantity())
                .createUserId(response.getCreateUserId())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupDatetime(response.getLastupDatetime())
                .isDelete(response.getIsDelete())
                .build();
    }
}

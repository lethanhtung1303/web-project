package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.ProductGetResponse;
import com.tdtu.webproject.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductGetUseCase {

    private final ProductService productService;

    public ProductGetUseCaseOutput getProduct(String productId) {
        return this.buildProductResponse(productService.getProduct(productId));
    }

    private ProductGetUseCaseOutput buildProductResponse(ProductGetResponse response) {
        return ProductGetUseCaseOutput.builder()
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

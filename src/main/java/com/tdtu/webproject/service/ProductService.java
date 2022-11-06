package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtProduct;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.ProductGetResponse;
import com.tdtu.webproject.model.ProductSearchCondition;
import com.tdtu.webproject.model.ProductSearchRequest;
import com.tdtu.webproject.model.ProductSearchResponse;
import com.tdtu.webproject.repository.ProductRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductGetResponse getProduct(String productId) {
        TdtProduct tdtProduct = productRepository.getProduct(NumberUtil.toBigDeimal(productId)
                .orElse(BigDecimal.ZERO));
        Optional.ofNullable(tdtProduct).orElseThrow(() -> new BusinessException("Not found"));
        return buildProductGetResponse(tdtProduct);

    }

    private ProductGetResponse buildProductGetResponse(TdtProduct tdtProduct) {
        return ProductGetResponse.builder()
                .productId(StringUtil.convertBigDecimalToString(tdtProduct.getProductId()))
                .productName(tdtProduct.getProductName())
                .approveStatus(tdtProduct.getApproveStatus())
                .amount(tdtProduct.getAmount())
                .quantity(BigDecimal.valueOf(tdtProduct.getQuantity()))
                .createUserId(tdtProduct.getCreateUserId())
                .createDatetime(DateUtil.parseLocalDate(tdtProduct.getCreateDatetime()))
                .lastupUserId(tdtProduct.getLastupUserId())
                .lastupDatetime(DateUtil.parseLocalDate(tdtProduct.getLastupDatetime()))
                .isDelete(tdtProduct.getIsDeleted())
                .build();
    }

    public Long count(ProductSearchRequest request) {
        return productRepository.countProduct(this.buildCondition(request));
    }

    public List<ProductSearchResponse> search(ProductSearchRequest request) {
        List<TdtProduct> tdtProducts = productRepository.searchProduct(this.buildCondition(request));
        return Optional.ofNullable(tdtProducts).isPresent()
                ? tdtProducts.stream()
                .map(this::buildProductSearchResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private ProductSearchCondition buildCondition(ProductSearchRequest request) {
        return ProductSearchCondition.builder()
                .productId( Optional.ofNullable(request.getProductId()).isPresent()
                        ? NumberUtil.toBigDeimal(request.getProductId()).get()
                        : BigDecimal.ZERO)
                .productName(request.getProductName())
                .offset(request.getOffset())
                .limit(request.getOffset())
                .createDatetimeFrom(DateUtil.parseLocalDateTime(request.getCreateDatetimeFrom()))
                .createDatetimeTo(DateUtil.parseLocalDateTime(request.getCreateDatetimeTo()))
                .build();
    }

    private ProductSearchResponse buildProductSearchResponse(TdtProduct tdtProduct) {
        return ProductSearchResponse.builder()
                .productId(StringUtil.convertBigDecimalToString(tdtProduct.getProductId()))
                .productName(tdtProduct.getProductName())
                .approveStatus(tdtProduct.getApproveStatus())
                .amount(tdtProduct.getAmount())
                .quantity(BigDecimal.valueOf(tdtProduct.getQuantity()))
                .createUserId(tdtProduct.getCreateUserId())
                .createDatetime(DateUtil.parseLocalDate(tdtProduct.getCreateDatetime()))
                .lastupUserId(tdtProduct.getLastupUserId())
                .lastupDatetime(DateUtil.parseLocalDate(tdtProduct.getLastupDatetime()))
                .isDelete(tdtProduct.getIsDeleted())
                .build();
    }
}

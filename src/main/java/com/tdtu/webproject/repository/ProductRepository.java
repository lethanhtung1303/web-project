package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtProduct;
import com.tdtu.webproject.model.ProductSearchCondition;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository {
    TdtProduct getProduct(BigDecimal productId);

    Long countProduct(ProductSearchCondition productSearchCondition);

    List<TdtProduct> searchProduct(ProductSearchCondition productSearchCondition);
}


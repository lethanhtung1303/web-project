package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtProductExample;
import com.tdtu.mbGenerator.generate.mybatis.example.TdtProductExample.Criteria;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtProductMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtProduct;
import com.tdtu.webproject.model.ProductSearchCondition;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class ProductRepositoryImp implements ProductRepository {

    private final TdtProductMapper tdtProductMapper;

    @Override
    public TdtProduct getProduct(BigDecimal productId) {
        TdtProductExample tdtProductExample = new TdtProductExample();
        Criteria criteria = tdtProductExample.createCriteria();
        Optional.ofNullable(productId).ifPresent(criteria::andProductIdEqualTo);
        return tdtProductMapper.selectByExample(tdtProductExample).stream()
                .filter(tdtProduct -> tdtProduct.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long countProduct(ProductSearchCondition condition) {
        TdtProductExample tdtProductExample = new TdtProductExample();
        Criteria criteria = tdtProductExample.createCriteria();
        Optional.ofNullable(condition.getProductId()).ifPresent(criteria::andProductIdEqualTo);
        Optional.ofNullable(condition.getProductName()).ifPresent(criteria::andProductNameEqualTo);
        Optional.ofNullable(condition.getCreateDatetimeFrom()).ifPresent(criteria::andCreateDatetimeGreaterThanOrEqualTo);
        Optional.ofNullable(condition.getCreateDatetimeTo()).ifPresent(criteria::andCreateDatetimeLessThan);
        criteria.andApproveStatusEqualTo("APPROVE");
        criteria.andIsDeletedEqualTo(false);
        return tdtProductMapper.countByExample(tdtProductExample);
    }

    @Override
    public List<TdtProduct> searchProduct(ProductSearchCondition condition) {
        TdtProductExample tdtProductExample = new TdtProductExample();
        Criteria criteria = tdtProductExample.createCriteria();
        Optional.ofNullable(condition.getProductId()).ifPresent(criteria::andProductIdEqualTo);
        Optional.ofNullable(condition.getProductName()).ifPresent(criteria::andProductNameLike);
        Optional.ofNullable(condition.getCreateDatetimeFrom()).ifPresent(criteria::andCreateDatetimeGreaterThanOrEqualTo);
        Optional.ofNullable(condition.getCreateDatetimeTo()).ifPresent(criteria::andCreateDatetimeLessThan);
        criteria.andApproveStatusEqualTo("APPROVE");
        criteria.andIsDeletedEqualTo(false);
        tdtProductExample.setOrderByClause("PRODUCT_ID DESC");
        boolean isSearch =
                Optional.ofNullable(condition.getOffset()).isPresent() && Optional.ofNullable(condition.getLimit()).isPresent() ||
                        condition.getOffset() != null && condition.getLimit() != null;
        return isSearch
                ? tdtProductMapper.selectByExampleWithRowbounds(tdtProductExample,
                new RowBounds(condition.getOffset(), condition.getLimit()))
                : tdtProductMapper.selectByExample(tdtProductExample);
    }
}

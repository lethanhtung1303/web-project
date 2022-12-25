package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtPropertyImgExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtPropertyImgMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyImgRepositoryImp implements PropertyImgRepository {
    private final TdtPropertyImgMapper tdtPropertyImg;
    @Override
    public List<TdtPropertyImg> selectAll(BigDecimal propertyId) {
        TdtPropertyImgExample example = new TdtPropertyImgExample();
        TdtPropertyImgExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyImg.selectByExample(example);
    }

    @Override
    public int create(TdtPropertyImg record) {
        return tdtPropertyImg.insertSelective(record);
    }

    @Override
    public int update(TdtPropertyImg record, BigDecimal propertyId) {
        TdtPropertyImgExample example = new TdtPropertyImgExample();
        TdtPropertyImgExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        Optional.ofNullable(record.getPropertyImgId()).ifPresent(criteria::andPropertyImgIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtPropertyImg.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(BigDecimal propertyId) {
        TdtPropertyImgExample example = new TdtPropertyImgExample();
        TdtPropertyImgExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(propertyId).ifPresent(criteria::andPropertyIdEqualTo);
        return tdtPropertyImg.updateByExampleSelective(
                TdtPropertyImg.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }
}

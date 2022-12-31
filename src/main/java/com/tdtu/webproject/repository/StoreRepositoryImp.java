package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtStorageExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtStorageMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtStorage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class StoreRepositoryImp implements StoreRepository {

    private TdtStorageMapper tdtStorageMapper;

    @Override
    public Long count(BigDecimal userId) {
        TdtStorageExample example = new TdtStorageExample();
        TdtStorageExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtStorageMapper.countByExample(example);
    }

    @Override
    public List<TdtStorage> search(BigDecimal userId) {
        TdtStorageExample example = new TdtStorageExample();
        TdtStorageExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtStorageMapper.selectByExample(example);
    }

    @Override
    public List<TdtStorage> selectAll() {
        return tdtStorageMapper.selectByExample(null);
    }

    @Override
    public int addStore(TdtStorage record) {
        return tdtStorageMapper.insertSelective(record);
    }

    @Override
    public int update(TdtStorage record) {
        TdtStorageExample example = new TdtStorageExample();
        TdtStorageExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(record.getUserId()).ifPresent(criteria::andUserIdEqualTo);
        Optional.ofNullable(record.getPropertyId()).ifPresent(criteria::andPropertyIdEqualTo);
        return tdtStorageMapper.updateByExampleSelective(record, example);
    }
}

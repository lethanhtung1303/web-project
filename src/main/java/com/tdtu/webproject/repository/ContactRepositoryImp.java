package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtContactExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtContactMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class ContactRepositoryImp implements ContactRepository {
    private final TdtContactMapper tdtContactMapper;

    @Override
    public int create(TdtContact record) {
        return tdtContactMapper.insertSelective(record);
    }

    @Override
    public int handle(TdtContact record) {
        TdtContactExample example = new TdtContactExample();
        TdtContactExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(record.getContactId()).ifPresent(criteria::andContactIdEqualTo);
        return tdtContactMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<TdtContact> selectAll() {
        TdtContactExample example = new TdtContactExample();
        TdtContactExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtContactMapper.selectByExample(example);
    }
}

package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtPropertyTypeExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtPropertyTypeMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@ComponentScan
public class PropertyTypeRepositoryImp implements PropertyTypeRepository {
    private final TdtPropertyTypeMapper tdtTypePropertyMapper;
    @Override
    public List<TdtPropertyType> selectAll() {
        TdtPropertyTypeExample example = new TdtPropertyTypeExample();
        TdtPropertyTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtTypePropertyMapper.selectByExample(example);
    }

    @Override
    public Long countAll() {
        TdtPropertyTypeExample example = new TdtPropertyTypeExample();
        TdtPropertyTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtTypePropertyMapper.countByExample(example);
    }
}

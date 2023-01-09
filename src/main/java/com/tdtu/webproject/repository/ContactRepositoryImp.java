package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtContactExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtContactMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import com.tdtu.webproject.model.ContactSearchCondition;
import com.tdtu.webproject.mybatis.mapper.ContactSupportMapper;
import com.tdtu.webproject.mybatis.result.ContactSearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class ContactRepositoryImp implements ContactRepository {
    private final TdtContactMapper tdtContactMapper;
    private final ContactSupportMapper contactSupportMapper;

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

    @Override
    public Long countContact(ContactSearchCondition condition) {
        return contactSupportMapper.count(condition);
    }

    @Override
    public List<ContactSearchResult> searchContact(ContactSearchCondition condition) {
        return contactSupportMapper.select(condition);
    }

    @Override
    public int delete(BigDecimal contactId) {
        TdtContactExample example = new TdtContactExample();
        TdtContactExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(contactId).ifPresent(criteria::andContactIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtContactMapper.updateByExampleSelective(
                TdtContact.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }
}

package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import com.tdtu.webproject.model.ContactSearchCondition;
import com.tdtu.webproject.mybatis.result.ContactSearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface ContactRepository {
    int create(TdtContact record);
    int handle(TdtContact record);

    List<TdtContact> selectAll();

    Long countContact(ContactSearchCondition condition);

    List<ContactSearchResult> searchContact(ContactSearchCondition condition);

    int delete(BigDecimal contactId);
}


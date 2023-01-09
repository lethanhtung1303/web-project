package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.ContactSearchCondition;
import com.tdtu.webproject.mybatis.result.ContactSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContactSupportMapper {
    Long count(ContactSearchCondition condition);

    List<ContactSearchResult> select(ContactSearchCondition condition);
}
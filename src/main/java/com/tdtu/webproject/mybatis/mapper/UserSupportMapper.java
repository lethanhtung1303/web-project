package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSupportMapper {
    List<UserSearchResult> select(UserSearchCondition condition);
    Long count(UserSearchCondition condition);
}
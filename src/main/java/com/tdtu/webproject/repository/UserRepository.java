package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.result.UserSearchResult;

import java.util.List;

public interface UserRepository {

    Long countUser(UserSearchCondition condition);
    List<UserSearchResult> searchUser(UserSearchCondition condition);
    int create(TdtUser record);
}


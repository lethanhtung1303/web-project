package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtUserMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.mapper.UserSupportMapper;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@ComponentScan
public class UserRepositoryImp implements UserRepository {

    private final UserSupportMapper userSupportMapper;
    private final TdtUserMapper tdtUserMapper;

    @Override
    public Long countUser(UserSearchCondition condition) {
        return userSupportMapper.count(condition);
    }

    @Override
    public List<UserSearchResult> searchUser(UserSearchCondition condition) {
        return userSupportMapper.select(condition);
    }

    @Override
    public int create(TdtUser record) {
        return tdtUserMapper.insert(record);
    }
}

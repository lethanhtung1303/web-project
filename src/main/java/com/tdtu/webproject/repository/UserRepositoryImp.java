package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtUserExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtUserMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.mapper.UserSupportMapper;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


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
    public List<TdtUser> selectAll() {
        TdtUserExample example = new TdtUserExample();
        TdtUserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtUserMapper.selectByExample(example);
    }

    @Override
    public int create(TdtUser record) {
        return tdtUserMapper.insert(record);
    }

    @Override
    public int delete(BigDecimal userId) {
        TdtUserExample example = new TdtUserExample();
        TdtUserExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtUserMapper.updateByExampleSelective(
                TdtUser.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }

    @Override
    public TdtUser getUser(BigDecimal userId) {
        TdtUserExample example = new TdtUserExample();
        TdtUserExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtUserMapper.selectByExample(example).stream()
                .filter(tdtProduct -> tdtProduct.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int changePassword(TdtUser record, BigDecimal userId) {
        TdtUserExample example = new TdtUserExample();
        TdtUserExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtUserMapper.updateByExample(record, example);
    }
}

package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtUserInfoExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtUserInfoMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;


@Repository
@AllArgsConstructor
@ComponentScan
public class UserInfoRepositoryImp implements UserInfoRepository {

    private TdtUserInfoMapper tdtUserInfoMapper;

    @Override
    public TdtUserInfo getUserInfo(BigDecimal userId) {
        TdtUserInfoExample example = new TdtUserInfoExample();
        TdtUserInfoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        return tdtUserInfoMapper.selectByExample(example).stream()
                .filter(tdtProduct -> tdtProduct.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int update(TdtUserInfo record, BigDecimal userId) {
        TdtUserInfoExample example = new TdtUserInfoExample();
        TdtUserInfoExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userId).ifPresent(criteria::andUserIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtUserInfoMapper.updateByExample(record, example);
    }

    @Override
    public int create(TdtUserInfo record) {
        return tdtUserInfoMapper.insert(record);
    }
}

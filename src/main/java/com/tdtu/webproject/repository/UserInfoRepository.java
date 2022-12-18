package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserInfo;

import java.math.BigDecimal;

public interface UserInfoRepository {
    TdtUserInfo getUserInfo(BigDecimal userId);
    int update(TdtUserInfo record, BigDecimal userId);
    int create(TdtUserInfo record);
}


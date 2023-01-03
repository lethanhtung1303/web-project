package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;

import java.util.List;

public interface ContactRepository {
    int create(TdtContact record);
    int handle(TdtContact record);

    List<TdtContact> selectAll();
}


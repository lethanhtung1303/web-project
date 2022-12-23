package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;

import java.util.List;

public interface PropertyImgRepository {

    List<TdtPropertyImg> selectAll();
}


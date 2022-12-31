package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNews;
import com.tdtu.webproject.model.NewsSearchCondition;
import com.tdtu.webproject.mybatis.result.NewsSearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface NewsRepository {

    Long countNews(NewsSearchCondition condition);

    List<TdtNews> selectAll();

    int create(TdtNews record);

    int update(TdtNews record, BigDecimal newsId);

    int delete(BigDecimal newsId);

    List<NewsSearchResult> searchNews(NewsSearchCondition condition);
}


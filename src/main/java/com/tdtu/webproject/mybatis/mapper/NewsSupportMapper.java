package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.NewsSearchCondition;
import com.tdtu.webproject.mybatis.result.NewsSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsSupportMapper {
    List<NewsSearchResult> select(NewsSearchCondition condition);
    Long count(NewsSearchCondition condition);
}
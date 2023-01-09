package com.tdtu.webproject.mybatis.mapper;

import com.tdtu.webproject.model.BookingSearchCondition;
import com.tdtu.webproject.mybatis.result.BookingSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookingSupportMapper {
    Long count(BookingSearchCondition condition);

    List<BookingSearchResult> select(BookingSearchCondition condition);
}
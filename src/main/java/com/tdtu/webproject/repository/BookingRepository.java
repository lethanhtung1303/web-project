package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtBooking;
import com.tdtu.webproject.model.BookingSearchCondition;
import com.tdtu.webproject.mybatis.result.BookingSearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface BookingRepository {
    int create(TdtBooking record);

    Long countBooking(BookingSearchCondition condition);

    List<BookingSearchResult> searchBooking(BookingSearchCondition condition);

    List<TdtBooking> selectAll();

    int update(TdtBooking record, BigDecimal bookingId);

    int delete(BigDecimal bookingId);
}


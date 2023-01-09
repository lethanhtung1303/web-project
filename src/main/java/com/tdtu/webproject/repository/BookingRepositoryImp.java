package com.tdtu.webproject.repository;

import com.tdtu.mbGenerator.generate.mybatis.example.TdtBookingExample;
import com.tdtu.mbGenerator.generate.mybatis.mapper.TdtBookingMapper;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtBooking;
import com.tdtu.webproject.model.BookingSearchCondition;
import com.tdtu.webproject.mybatis.mapper.BookingSupportMapper;
import com.tdtu.webproject.mybatis.result.BookingSearchResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ComponentScan
public class BookingRepositoryImp  implements BookingRepository {
    private final TdtBookingMapper tdtBookingMapper;
    private final BookingSupportMapper bookingSupportMapper;

    @Override
    public int create(TdtBooking record) {
        return tdtBookingMapper.insertSelective(record);
    }

    @Override
    public Long countBooking(BookingSearchCondition condition) {
        return bookingSupportMapper.count(condition);
    }

    @Override
    public List<BookingSearchResult> searchBooking(BookingSearchCondition condition) {
        return bookingSupportMapper.select(condition);
    }

    @Override
    public List<TdtBooking> selectAll() {
        TdtBookingExample example = new TdtBookingExample();
        TdtBookingExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        return tdtBookingMapper.selectByExample(example);
    }

    @Override
    public int update(TdtBooking record, BigDecimal bookingId) {
        TdtBookingExample example = new TdtBookingExample();
        TdtBookingExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(bookingId).ifPresent(criteria::andBookingIdEqualTo);
        return tdtBookingMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(BigDecimal bookingId) {
        TdtBookingExample example = new TdtBookingExample();
        TdtBookingExample.Criteria criteria = example.createCriteria();
        Optional.ofNullable(bookingId).ifPresent(criteria::andBookingIdEqualTo);
        criteria.andIsDeletedEqualTo(false);
        return tdtBookingMapper.updateByExampleSelective(
                TdtBooking.builder()
                        .isDeleted(true)
                        .build(),
                example);
    }
}


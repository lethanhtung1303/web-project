package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtBooking;
import com.tdtu.webproject.repository.BookingRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingManageService {
    private final BookingRepository bookingRepository;

    public boolean checkExistBooking(BigDecimal bookingId){
        List<TdtBooking> results = this.getAllBooking().stream()
                .filter(contact -> contact.getBookingId().equals(bookingId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public List<TdtBooking> getAllBooking(){
        return bookingRepository.selectAll();
    }
}

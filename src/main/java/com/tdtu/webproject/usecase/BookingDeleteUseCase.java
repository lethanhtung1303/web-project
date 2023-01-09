package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingDeleteUseCase {
    private final BookingService bookingService;
    public String deleteBooking(String bookingId) {
        return bookingService.deleteBooking(bookingId);
    }
}

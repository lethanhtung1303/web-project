package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingUpdateUseCase {

    private final BookingService bookingService;
    public String updateBooking(String bookingId, String approveStatus, String lastupUserId) {
        return bookingService.updateBooking(bookingId, approveStatus, lastupUserId);
    }
}

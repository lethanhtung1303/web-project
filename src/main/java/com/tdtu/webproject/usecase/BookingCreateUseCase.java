package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.BookingCreateRequest;
import com.tdtu.webproject.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingCreateUseCase {
    private final BookingService bookingService;
    public String createBooking(BookingCreateUseCaseInput input) {
        BookingCreateRequest request = this.buildBookingCreateRequest(input);
        return bookingService.createBooking(request);
    }
    private BookingCreateRequest buildBookingCreateRequest(BookingCreateUseCaseInput input) {
        return BookingCreateRequest.builder()
                .propertyId(input.getPropertyId())
                .bookingDate(input.getBookingDate())
                .note(input.getNote())
                .createUserId(input.getCreateUserId())
                .build();
    }
}

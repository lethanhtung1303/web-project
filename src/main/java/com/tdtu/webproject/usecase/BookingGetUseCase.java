package com.tdtu.webproject.usecase;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.BookingSearchRequest;
import com.tdtu.webproject.model.BookingSearchResponse;
import com.tdtu.webproject.service.BookingService;
import com.tdtu.webproject.usecase.BookingSearchUseCaseOutput.BookingSearchUseCaseResults;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingGetUseCase {

    private final BookingService bookingService;
    public BookingSearchUseCaseResults getBooking(String bookingId) {
        List<BookingSearchResponse> bookingList = bookingService.search(BookingSearchRequest.builder()
                .bookingId(bookingId)
                .build());
        if (!ArrayUtil.isNotNullOrEmptyList(bookingList)){
            throw new BusinessException("Not found booking!");
        }
        return bookingList.stream()
                .filter(booking -> booking.getBookingId().equals(bookingId))
                .findFirst()
                .map(booking -> BookingSearchUseCaseResults.builder()
                        .bookingId(booking.getBookingId())
                        .propertyId(booking.getPropertyId())
                        .propertyTitle(booking.getPropertyTitle())
                        .propertyOwnerId(booking.getPropertyOwnerId())
                        .propertyOwnerName(booking.getPropertyOwnerName())
                        .propertyOwnerPhone(booking.getPropertyOwnerPhone())
                        .propertyOwnerEmail(booking.getPropertyOwnerEmail())
                        .bookingDate(booking.getBookingDate())
                        .note(booking.getNote())
                        .approveStatus(booking.getApproveStatus())
                        .createUserId(booking.getCreateUserId())
                        .createUserName(booking.getCreateUserName())
                        .bookingUserPhone(booking.getBookingUserPhone())
                        .bookingUserEmail(booking.getBookingUserEmail())
                        .createDatetime(booking.getCreateDatetime())
                        .lastupUserId(booking.getLastupUserId())
                        .lastupUserName(booking.getLastupUserName())
                        .lastupDatetime(booking.getLastupDatetime())
                        .build())
                .orElse(null);
    }
}

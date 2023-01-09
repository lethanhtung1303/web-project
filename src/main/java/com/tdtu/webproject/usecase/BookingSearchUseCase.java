package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.BookingSearchRequest;
import com.tdtu.webproject.model.BookingSearchResponse;
import com.tdtu.webproject.service.BookingService;
import com.tdtu.webproject.usecase.BookingSearchUseCaseOutput.BookingSearchUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingSearchUseCase {

    private final BookingService bookingService;
    public BookingSearchUseCaseOutput searchBooking(BookingSearchUseCaseInput input) {
        BookingSearchRequest request = this.buildBookingSearchRequest(input);
        return BookingSearchUseCaseOutput.builder()
                .resultsTotalCount(bookingService.count(request))
                .bookings(bookingService.search(request).stream()
                        .map(this::buildBookingSearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private BookingSearchRequest buildBookingSearchRequest(BookingSearchUseCaseInput input) {
        return BookingSearchRequest.builder()
                .propertyOwnerId(input.getPropertyOwnerId())
                .createUserId(input.getCreateUserId())
                .bookingDateFrom(input.getBookingDateFrom())
                .bookingDateTo(input.getBookingDateTo())
                .createDatetimeFrom(input.getCreateDatetimeFrom())
                .createDatetimeTo(input.getCreateDatetimeTo())
                .propertyTitle(input.getPropertyTitle())
                .approveStatus(input.getApproveStatus())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .build();
    }

    private BookingSearchUseCaseResults buildBookingSearchUseCaseResults(BookingSearchResponse response) {
        return BookingSearchUseCaseResults.builder()
                .bookingId(response.getBookingId())
                .propertyId(response.getPropertyId())
                .propertyTitle(response.getPropertyTitle())
                .propertyOwnerId(response.getPropertyOwnerId())
                .propertyOwnerName(response.getPropertyOwnerName())
                .propertyOwnerPhone(response.getPropertyOwnerPhone())
                .propertyOwnerEmail(response.getPropertyOwnerEmail())
                .bookingDate(response.getBookingDate())
                .note(response.getNote())
                .approveStatus(response.getApproveStatus())
                .createUserId(response.getCreateUserId())
                .createUserName(response.getCreateUserName())
                .bookingUserPhone(response.getBookingUserPhone())
                .bookingUserEmail(response.getBookingUserEmail())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupUserName(response.getLastupUserName())
                .lastupDatetime(response.getLastupDatetime())
                .build();
    }
}

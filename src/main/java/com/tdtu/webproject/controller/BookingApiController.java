package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import com.tdtu.webproject.usecase.BookingSearchUseCaseOutput.BookingSearchUseCaseResults;
import generater.openapi.api.BookingApi;
import generater.openapi.model.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class BookingApiController implements BookingApi {

    private final BookingCreateUseCase bookingCreateUseCase;
    private final BookingGetUseCase bookingGetUseCase;
    private final BookingSearchUseCase bookingSearchUseCase;
    private final BookingUpdateUseCase bookingUpdateUseCase;
    private final BookingDeleteUseCase bookingDeleteUseCase;

    @Override
    public ResponseEntity<BookingCreateResponse> createBooking(
            @ApiParam(value = "")
            @Valid
            @RequestBody BookingCreateRequest bookingCreateRequest,
            BindingResult bindingResult1) {
        BookingCreateUseCaseInput input = this.buildBookingCreateUseCaseInput(bookingCreateRequest);
        return ResponseEntity.ok(BookingCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(bookingCreateUseCase.createBooking(input))
                .build());
    }

    private BookingCreateUseCaseInput buildBookingCreateUseCaseInput(BookingCreateRequest request) {
        return BookingCreateUseCaseInput.builder()
                .propertyId(request.getPropertyId())
                .bookingDate(request.getBookingDate())
                .note(request.getNote())
                .createUserId(request.getCreateUserId())
                .build();
    }

    @Override
    public ResponseEntity<BookingGetResponse> getBooking(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "bookingId", required = true)
            String bookingId ) {
        BookingSearchUseCaseResults output = bookingGetUseCase.getBooking(bookingId);
        return ResponseEntity.ok(BookingGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(Optional.ofNullable(output).isPresent()
                        ? List.of(this.buildBookingResponse(output))
                        : Collections.emptyList())
                .build());
    }

    private BookingResponse buildBookingResponse(BookingSearchUseCaseResults output) {
        return BookingResponse.builder()
                .bookingId(output.getBookingId())
                .propertyId(output.getPropertyId())
                .propertyTitle(output.getPropertyTitle())
                .propertyOwnerId(output.getPropertyOwnerId())
                .propertyOwnerName(output.getPropertyOwnerName())
                .propertyOwnerPhone(output.getPropertyOwnerPhone())
                .propertyOwnerEmail(output.getPropertyOwnerEmail())
                .bookingDate(output.getBookingDate())
                .note(output.getNote())
                .approveStatus(output.getApproveStatus())
                .createUserId(output.getCreateUserId())
                .createUserName(output.getCreateUserName())
                .bookingUserPhone(output.getBookingUserPhone())
                .bookingUserEmail(output.getBookingUserEmail())
                .createDatetime(output.getCreateDatetime())
                .lastupUserId(output.getLastupUserId())
                .lastupUserName(output.getLastupUserName())
                .lastupDatetime(output.getLastupDatetime())
                .build();
    }

    @Override
    public ResponseEntity<SearchBookingResponse> searchBooking(
            @ApiParam(value = "")
            @Valid
            @RequestBody SearchBookingRequest searchBookingRequest,
            BindingResult bindingResult1) {
        BookingSearchUseCaseOutput output = bookingSearchUseCase.searchBooking(this.buildBookingSearchUseCaseInput(searchBookingRequest));
        return ResponseEntity.ok(SearchBookingResponse.builder()
                .status(HttpStatus.OK.value())
                .results(SearchBookingResponseResults.builder()
                        .bookings(output.getBookings().stream()
                                .map(this::buildBookingResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private BookingSearchUseCaseInput buildBookingSearchUseCaseInput(SearchBookingRequest request) {
        return BookingSearchUseCaseInput.builder()
                .propertyOwnerId(request.getPropertyOwnerId())
                .createUserId(request.getCreateUserId())
                .bookingDateFrom(request.getBookingDateFrom())
                .bookingDateTo(request.getBookingDateTo())
                .createDatetimeFrom(request.getCreateDatetimeFrom())
                .createDatetimeTo(request.getCreateDatetimeTo())
                .propertyTitle(request.getPropertyTitle())
                .approveStatus(request.getApproveStatus())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    @Override
    public ResponseEntity<BookingUpdateResponse> updateBooking(
            @ApiParam(value = "")
            @Valid
            @RequestBody BookingUpdateRequest bookingUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(BookingUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(bookingUpdateUseCase.updateBooking(
                        bookingUpdateRequest.getBookingId(),
                        bookingUpdateRequest.getApproveStatus(),
                        bookingUpdateRequest.getLastupUserId()
                ))
                .build());
    }

    @Override
    public ResponseEntity<BookingDeleteResponse> deleteBooking(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "bookingId", required = true)
            String bookingId ) {
        return ResponseEntity.ok(BookingDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(bookingDeleteUseCase.deleteBooking(bookingId))
                .build());
    }
}

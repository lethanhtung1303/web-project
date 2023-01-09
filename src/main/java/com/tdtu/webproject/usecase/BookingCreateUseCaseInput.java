package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingCreateUseCaseInput {
    private String propertyId;
    private String bookingDate;
    private String createUserId;
    private String note;
}

package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingCreateRequest {
    private String propertyId;
    private String bookingDate;
    private String createUserId;
    private String note;
}

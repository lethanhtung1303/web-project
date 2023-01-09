package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingSearchUseCaseInput {
    private String propertyOwnerId;
    private String createUserId;
    private String bookingDateFrom;
    private String bookingDateTo;
    private String createDatetimeFrom;
    private String createDatetimeTo;
    private String propertyTitle;
    private String approveStatus;
    private Integer offset;
    private Integer limit;
}

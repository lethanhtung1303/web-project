package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookingSearchCondition {
    private BigDecimal bookingId;
    private List<BigDecimal> propertyIdList;
    private LocalDateTime bookingDateFrom;
    private LocalDateTime bookingDateTo;
    private String createUserId;
    private LocalDateTime createDatetimeFrom;
    private LocalDateTime createDatetimeTo;
    private String approveStatus;
    private Integer offset;
    private Integer limit;
}

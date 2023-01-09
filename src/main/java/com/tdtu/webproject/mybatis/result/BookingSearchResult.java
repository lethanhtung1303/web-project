package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingSearchResult {
    private BigDecimal bookingId;
    private BigDecimal propertyId;
    private LocalDateTime bookingDate;
    private String note;
    private String approveStatus;
    private String createUserId;
    private LocalDateTime createDatetime;
    private String lastupUserId;
    private LocalDateTime lastupDatetime;
}

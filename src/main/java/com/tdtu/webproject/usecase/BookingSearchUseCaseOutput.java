package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookingSearchUseCaseOutput {
    private Long resultsTotalCount;
    private List<BookingSearchUseCaseResults> bookings;
    @Data
    @Builder
    @AllArgsConstructor
    public static class BookingSearchUseCaseResults {
        private String bookingId;
        private String propertyId;
        private String propertyTitle;
        private String propertyOwnerId;
        private String propertyOwnerName;
        private String propertyOwnerPhone;
        private String propertyOwnerEmail;
        private String bookingDate;
        private String note;
        private String approveStatus;
        private String createUserId;
        private String createUserName;
        private String bookingUserPhone;
        private String bookingUserEmail;
        private String createDatetime;
        private String lastupUserId;
        private String lastupUserName;
        private String lastupDatetime;
    }
}

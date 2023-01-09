package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingSearchResponse {
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

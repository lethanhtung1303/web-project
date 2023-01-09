package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContactSearchRequest {
    private String contactId;
    private String email;
    private String content;
    private String phone;
    private String createDatetimeFrom;
    private String createDatetimeTo;
    private String approveStatus;
    private Integer offset;
    private Integer limit;
}

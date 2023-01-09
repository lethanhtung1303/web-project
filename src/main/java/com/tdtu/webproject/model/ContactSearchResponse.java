package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContactSearchResponse {
    private String contactId;
    private String content;
    private String email;
    private String phone;
    private String approveStatus;
    private String handlerId;
    private String handlerName;
    private String createDatetime;
    private String lastupUserId;
    private String lastupUserName;
    private String lastupDatetime;
}

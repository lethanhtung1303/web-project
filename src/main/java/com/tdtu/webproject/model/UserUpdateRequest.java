package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserUpdateRequest {
    private String userId;
    private String stdId;
    private String userName;
    private String address;
    private String idCard;
    private String fCard;
    private String bCard;
    private String portrait;
    private String lastupUserId;
}

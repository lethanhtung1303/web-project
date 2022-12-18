package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSearchRequest {
    private String userId;
    private String email;
    private String stdId;
    private String userName;
    private String address;
    private String idCard;
    private String phone;
    private Integer offset;
    private Integer limit;
}

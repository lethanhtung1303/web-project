package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSearchResponse {
    private String userId;
    private String userName;
    private String approveStatus;
    private String stdId;
    private String address;
    private String idCard;
    private String fCard;
    private String bCard;
    private String portrait;
    private String phone;
    private String email;
    private String password;
    private String roleId;
    private String roleName;
    private String createUserId;
    private String createUserName;
    private String createDatetime;
    private String lastupUserId;
    private String lastupUserName;
    private String lastupDatetime;
}

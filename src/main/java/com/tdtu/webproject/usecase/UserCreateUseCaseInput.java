package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserCreateUseCaseInput {
    private String email;
    private String password;
    private String roleId;
    private String createUserId;
    private String stdId;
    private String userName;
    private String address;
    private String idCard;
    private String fCard;
    private String bCard;
    private String portrait;
    private String phone;
}

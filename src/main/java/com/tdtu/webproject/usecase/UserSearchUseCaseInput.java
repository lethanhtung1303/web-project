package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSearchUseCaseInput {
    private String email;
    private String stdId;
    private String userName;
    private String address;
    private String idCard;
    private String phone;
    private Integer offset;
    private Integer limit;
}

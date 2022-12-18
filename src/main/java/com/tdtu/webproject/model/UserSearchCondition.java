package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class UserSearchCondition {
    private BigDecimal userId;
    private String email;
    private String stdId;
    private String userName;
    private String address;
    private String idCard;
    private String phone;
    private Integer offset;
    private Integer limit;
}

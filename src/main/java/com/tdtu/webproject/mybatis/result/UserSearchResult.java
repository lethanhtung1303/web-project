package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class UserSearchResult {
    private BigDecimal userId;
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
    private BigDecimal roleId;
    private String roleName;
    private BigDecimal createUserId;
    private LocalDateTime createDatetime;
    private BigDecimal lastupUserId;
    private LocalDateTime lastupDatetime;
}

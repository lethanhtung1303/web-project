package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ContactSearchResult {
    private BigDecimal contactId;
    private String content;
    private String email;
    private String phone;
    private String approveStatus;
    private BigDecimal handlerId;
    private LocalDateTime createDatetime;
    private BigDecimal lastupUserId;
    private LocalDateTime lastupDatetime;
}

package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ContactSearchCondition {
    private BigDecimal contactId;
    private String email;
    private String content;
    private String phone;
    private LocalDateTime createDatetimeFrom;
    private LocalDateTime createDatetimeTo;
    private String approveStatus;
    private Integer offset;
    private Integer limit;
}

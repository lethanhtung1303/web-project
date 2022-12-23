package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PropertySearchCondition {
    private BigDecimal userId;
    private BigDecimal typeId;
    private String title;
    private String address;
    private LocalDateTime createDatetimeFrom;
    private LocalDateTime createDatetimeTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal areaFrom;
    private BigDecimal areaTo;
    private Integer offset;
    private Integer limit;
}

package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PropertySearchResult {
    private BigDecimal propertyId;
    private BigDecimal typeId;
    private String typeName;
    private String createUserId;
    private LocalDateTime createDatetime;
    private String lastupUserId;
    private LocalDateTime lastupDatetime;
    private String title;
    private String address;
    private BigDecimal amount;
    private BigDecimal area;
    private String description;
    private String approveStatus;
}

package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProductSearchCondition {
    private BigDecimal productId;
    private String productName;
    private Integer offset;
    private Integer limit;
    private LocalDateTime createDatetimeFrom;
    private LocalDateTime createDatetimeTo;
}

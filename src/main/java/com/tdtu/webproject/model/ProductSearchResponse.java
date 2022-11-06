package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProductSearchResponse {
    private String productId;
    private String productName;
    private String approveStatus;
    private BigDecimal amount;
    private BigDecimal quantity;
    private String createUserId;
    private LocalDate createDatetime;
    private String lastupUserId;
    private LocalDate lastupDatetime;
    private Boolean isDelete;
}

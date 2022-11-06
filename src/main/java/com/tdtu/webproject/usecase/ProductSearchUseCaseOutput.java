package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductSearchUseCaseOutput {
    private Long resultsTotalCount;
    private List<ProductSearchUseCaseResults> products;
    @Data
    @Builder
    @AllArgsConstructor
    public static class ProductSearchUseCaseResults{
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
}

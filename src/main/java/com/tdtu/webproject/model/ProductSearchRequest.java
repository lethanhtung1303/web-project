package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProductSearchRequest {
    private String productId;
    private String productName;
    private Integer offset;
    private Integer limit;
    private LocalDate createDatetimeFrom;
    private LocalDate createDatetimeTo;
}

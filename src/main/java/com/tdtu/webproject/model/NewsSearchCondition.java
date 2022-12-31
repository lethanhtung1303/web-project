package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewsSearchCondition {
    private BigDecimal newsId;
    private String title;
    private List<String> userIdList;
    private LocalDateTime createDatetimeFrom;
    private LocalDateTime createDatetimeTo;
    private Integer offset;
    private Integer limit;
}

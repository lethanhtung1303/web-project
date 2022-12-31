package com.tdtu.webproject.mybatis.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class NewsSearchResult {
    private BigDecimal newsId;
    private String title;
    private String content;
    private String cover;
    private BigDecimal createUserId;
    private LocalDateTime createDatetime;
    private BigDecimal lastupUserId;
    private LocalDateTime lastupDatetime;
}

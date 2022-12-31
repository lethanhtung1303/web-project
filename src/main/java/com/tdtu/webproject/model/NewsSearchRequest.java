package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsSearchRequest {
    private String newsId;
    private String title;
    private String userName;
    private String createDatetimeFrom;
    private String createDatetimeTo;
    private Integer offset;
    private Integer limit;
}

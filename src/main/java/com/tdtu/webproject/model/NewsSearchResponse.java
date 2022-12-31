package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsSearchResponse {
    private String newsId;
    private String title;
    private String content;
    private String cover;
    private String createUserId;
    private String createUserName;
    private String createDatetime;
    private String lastupUserId;
    private String lastupUserName;
    private String lastupDatetime;
}

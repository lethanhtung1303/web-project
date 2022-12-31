package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsCreateRequest {
    private String newsId;
    private String title;
    private String content;
    private String createUserId;
    private String lastupUserId;
    private String cover;
}

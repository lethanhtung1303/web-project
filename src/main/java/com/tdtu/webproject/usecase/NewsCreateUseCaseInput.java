package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsCreateUseCaseInput {
    private String newsId;
    private String title;
    private String content;
    private String createUserId;
    private String lastupUserId;
    private String cover;
}

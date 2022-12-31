package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewsSearchUseCaseOutput {
    private Long resultsTotalCount;
    private List<NewsSearchUseCaseResults> news;
    @Data
    @Builder
    @AllArgsConstructor
    public static class NewsSearchUseCaseResults {
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
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.NewsSearchRequest;
import com.tdtu.webproject.model.NewsSearchResponse;
import com.tdtu.webproject.service.NewsService;
import com.tdtu.webproject.usecase.NewsSearchUseCaseOutput.NewsSearchUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class NewsSearchUseCase {
    private final NewsService newsService;
    public NewsSearchUseCaseOutput searchNews(NewsSearchUseCaseInput input) {
        NewsSearchRequest request = this.buildNewsSearchRequest(input);
        return NewsSearchUseCaseOutput.builder()
                .resultsTotalCount(newsService.count(request))
                .news(newsService.search(request).stream()
                        .map(this::buildNewsSearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private NewsSearchRequest buildNewsSearchRequest(NewsSearchUseCaseInput input) {
        return NewsSearchRequest.builder()
                .title(input.getTitle())
                .userName(input.getUserName())
                .createDatetimeFrom(input.getCreateDatetimeFrom())
                .createDatetimeTo(input.getCreateDatetimeTo())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .build();
    }

    private NewsSearchUseCaseResults buildNewsSearchUseCaseResults(NewsSearchResponse response) {
        return NewsSearchUseCaseResults.builder()
                .newsId(response.getNewsId())
                .title(response.getTitle())
                .content(response.getContent())
                .cover(response.getCover())
                .createUserId(response.getCreateUserId())
                .createUserName(response.getCreateUserName())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupUserName(response.getLastupUserName())
                .lastupDatetime(response.getLastupDatetime())
                .build();
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.NewsCreateRequest;
import com.tdtu.webproject.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class NewsUpdateUseCase {

    private final NewsService newsService;
    public String updateNews(NewsCreateUseCaseInput input) {
        NewsCreateRequest request = this.buildNewsCreateRequest(input);
        return newsService.updateNews(request);
    }

    private NewsCreateRequest buildNewsCreateRequest(NewsCreateUseCaseInput input) {
        return NewsCreateRequest.builder()
                .newsId(input.getNewsId())
                .title(input.getTitle())
                .content(input.getContent())
                .cover(input.getCover())
                .lastupUserId(input.getLastupUserId())
                .build();
    }
}

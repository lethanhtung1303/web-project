package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.NewsCreateRequest;
import com.tdtu.webproject.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class NewsCreateUseCase {

    private final NewsService newsService;
    public String createNews(NewsCreateUseCaseInput input) {
        NewsCreateRequest request = this.buildNewsCreateRequest(input);
        return newsService.createNews(request);
    }

    private NewsCreateRequest buildNewsCreateRequest(NewsCreateUseCaseInput input) {
        return NewsCreateRequest.builder()
                .title(input.getTitle())
                .content(input.getContent())
                .cover(input.getCover())
                .createUserId(input.getCreateUserId())
                .build();
    }
}

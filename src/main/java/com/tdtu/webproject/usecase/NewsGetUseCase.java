package com.tdtu.webproject.usecase;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.NewsSearchRequest;
import com.tdtu.webproject.model.NewsSearchResponse;
import com.tdtu.webproject.service.NewsService;
import com.tdtu.webproject.usecase.NewsSearchUseCaseOutput.NewsSearchUseCaseResults;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class NewsGetUseCase {
    private final NewsService newsService;
    public NewsSearchUseCaseResults getNews(String newsId) {
        List<NewsSearchResponse> newsList = newsService.search(NewsSearchRequest.builder()
                .newsId(newsId)
                .build());
        if (!ArrayUtil.isNotNullOrEmptyList(newsList)){
            throw new BusinessException("Not found news!");
        }
        return newsList.stream()
                .filter(news -> news.getNewsId().equals(newsId))
                .findFirst()
                .map(news -> NewsSearchUseCaseResults.builder()
                        .newsId(news.getNewsId())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .cover(news.getCover())
                        .createUserId(news.getCreateUserId())
                        .createUserName(news.getCreateUserName())
                        .createDatetime(news.getCreateDatetime())
                        .lastupUserId(news.getLastupUserId())
                        .lastupUserName(news.getLastupUserName())
                        .lastupDatetime(news.getLastupDatetime())
                        .build())
                .orElse(null);
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class NewsDeleteUseCase {
    private final NewsService newsService;
    public String deleteNews(String newsId) {
        return newsService.deleteNews(newsId);
    }

}

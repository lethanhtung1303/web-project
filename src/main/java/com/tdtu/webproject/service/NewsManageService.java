package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNews;
import com.tdtu.webproject.repository.NewsRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsManageService {
    private final NewsRepository newsRepository;

    public boolean checkExistNews(BigDecimal newsId){
        List<TdtNews> results = this.getAllNews().stream()
                .filter(news -> news.getNewsId().equals(newsId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public List<TdtNews> getAllNews(){
        return newsRepository.selectAll();
    }
}

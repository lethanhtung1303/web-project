package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtNews;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.NewsCreateRequest;
import com.tdtu.webproject.model.NewsSearchCondition;
import com.tdtu.webproject.model.NewsSearchRequest;
import com.tdtu.webproject.model.NewsSearchResponse;
import com.tdtu.webproject.mybatis.result.NewsSearchResult;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.NewsRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsManageService newsManageService;
    private final UserManageService userManageService;

    public String createNews(NewsCreateRequest request) {
        BigDecimal newsId = this.generatorNewsId();
        if (Optional.ofNullable(newsId).isPresent()){
            if (newsManageService.checkExistNews(newsId)){
                throw new BusinessException("News already exists in the system!");
            }
            return  newsRepository.create(this.buildTdtNews(newsId, request)) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtNews buildTdtNews(BigDecimal newsId, NewsCreateRequest request) {
        return TdtNews.builder()
                .newsId(newsId)
                .title(request.getTitle())
                .content(request.getContent())
                .cover(request.getCover())
                .createUserId(request.getCreateUserId())
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }

    private BigDecimal generatorNewsId() {
        String propertyId = String.valueOf(DateUtil.getTimeNow().getYear()).substring(2)
                + DateUtil.getTimeNow().getMonthValue()
                + String.format("%03d", newsRepository.countNews(NewsSearchCondition.builder().build()) + 1);
        return NumberUtil.toBigDeimal(propertyId).orElse(null);
    }

    public String updateNews(NewsCreateRequest request) {
        BigDecimal newsId = NumberUtil.toBigDeimal(request.getNewsId()).orElse(null);
        if (Optional.ofNullable(newsId).isPresent()){
            if (!newsManageService.checkExistNews(newsId)){
                throw new BusinessException("Not found news!");
            }
            return  newsRepository.update(this.buildTdtNewsForUpdate(request), newsId) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtNews buildTdtNewsForUpdate(NewsCreateRequest request) {
        return TdtNews.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .cover(request.getCover())
                .lastupUserId(request.getLastupUserId())
                .lastupDatetime(DateUtil.getTimeNow())
                .build();
    }

    public String deleteNews(String newsId) {
        BigDecimal id = NumberUtil.toBigDeimal(newsId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            if (!newsManageService.checkExistNews(id)){
                throw new BusinessException("Not found news!");
            }
            return newsRepository.delete(id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    public List<NewsSearchResponse> search(NewsSearchRequest request) {
        NewsSearchCondition condition  = this.buildNewsSearchCondition(request);
        List<NewsSearchResult> newsSearchResultList = Optional.ofNullable(condition).isPresent()
                ? newsRepository.searchNews(condition)
                : Collections.emptyList();
        return Optional.ofNullable(newsSearchResultList).isPresent()
                ? newsSearchResultList.stream()
                .map(this::buildNewsSearchResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    public Long count(NewsSearchRequest request) {
        NewsSearchCondition condition  = this.buildNewsSearchCondition(request);
        return Optional.ofNullable(condition).isPresent()
                ? newsRepository.countNews(condition)
                : 0L;
    }

    private NewsSearchCondition buildNewsSearchCondition(NewsSearchRequest request) {
        List<String> userIdList = new ArrayList<>();
        if (Optional.ofNullable(request.getUserName()).isPresent()){
            userManageService.getUserIdByUserName(userManageService.getAllUserResult(), request.getUserName())
                    .forEach(userId -> userIdList.add(StringUtil.convertBigDecimalToString(userId)));
        }
        if (Optional.ofNullable(request.getUserName()).isPresent() && !ArrayUtil.isNotNullOrEmptyList(userIdList)){
            return null;
        }
        String createDatetimeFrom = request.getCreateDatetimeFrom();
        String createDatetimeTo = request.getCreateDatetimeTo();
        return NewsSearchCondition.builder()
                .newsId(NumberUtil.toBigDeimal(request.getNewsId()).orElse(null))
                .title(request.getTitle())
                .userIdList(userIdList)
                .createDatetimeFrom(Optional.ofNullable(createDatetimeFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(
                                DateUtil.parseLocalDate(createDatetimeFrom, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .createDatetimeTo(Optional.ofNullable(createDatetimeTo).isPresent()
                        ? DateUtil.parseLocalDateTime(
                                DateUtil.parseLocalDate(createDatetimeTo, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .build();
    }

    private NewsSearchResponse buildNewsSearchResponse(NewsSearchResult result) {
        List<UserSearchResult> userList = userManageService.getAllUserResult();
        BigDecimal createUserId = result.getCreateUserId();
        LocalDateTime createDatetime = result.getCreateDatetime();
        BigDecimal lastupUserId = result.getLastupUserId();
        LocalDateTime lastupDatetime = result.getLastupDatetime();
        return NewsSearchResponse.builder()
                .newsId(StringUtil.convertBigDecimalToString(result.getNewsId()))
                .title(result.getTitle())
                .content(result.getContent())
                .cover(result.getCover())
                .createUserId(StringUtil.convertBigDecimalToString(createUserId))
                .createUserName(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserName(userList, createUserId)
                        : null)
                .createDatetime(Optional.ofNullable(createDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(createDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .lastupUserId(StringUtil.convertBigDecimalToString(lastupUserId))
                .lastupUserName(Optional.ofNullable(lastupUserId).isPresent()
                        ? userManageService.getUserName(userList, lastupUserId)
                        : null)
                .lastupDatetime(Optional.ofNullable(lastupDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lastupDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .build();
    }
}

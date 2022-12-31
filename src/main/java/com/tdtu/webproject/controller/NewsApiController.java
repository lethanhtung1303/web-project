package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import com.tdtu.webproject.usecase.NewsSearchUseCaseOutput.NewsSearchUseCaseResults;
import generater.openapi.api.NewsApi;
import generater.openapi.model.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class NewsApiController implements NewsApi {
    private final NewsCreateUseCase newsCreateUseCase;
    private final NewsUpdateUseCase newsUpdateUseCase;
    private final NewsDeleteUseCase newsDeleteUseCase;
    private final NewsSearchUseCase newsSearchUseCase;
    private final NewsGetUseCase newsGetUseCase;

    @Override
    public ResponseEntity<NewsCreateResponse> createNews(
            @ApiParam(value = "")
            @Valid
            @RequestBody NewsCreateRequest newsCreateRequest,
            BindingResult bindingResult1) {
        NewsCreateUseCaseInput input = this.buildNewsCreateUseCaseInput(newsCreateRequest);
        return ResponseEntity.ok(NewsCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(newsCreateUseCase.createNews(input))
                .build());
    }

    private NewsCreateUseCaseInput buildNewsCreateUseCaseInput(NewsCreateRequest request) {
        return NewsCreateUseCaseInput.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .cover(request.getCover())
                .createUserId(request.getCreateUserId())
                .build();
    }

    @Override
    public ResponseEntity<NewsUpdateResponse> updateNews(
            @ApiParam(value = "")
            @Valid
            @RequestBody NewsUpdateRequest newsUpdateRequest,
            BindingResult bindingResult1) {
        NewsCreateUseCaseInput input = this.buildNewsCreateUseCaseInput(newsUpdateRequest);
        return ResponseEntity.ok(NewsUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(newsUpdateUseCase.updateNews(input))
                .build());
    }

    private NewsCreateUseCaseInput buildNewsCreateUseCaseInput(NewsUpdateRequest request) {
        return NewsCreateUseCaseInput.builder()
                .newsId(request.getNewsId())
                .title(request.getTitle())
                .content(request.getContent())
                .cover(request.getCover())
                .lastupUserId(request.getLastupUserId())
                .build();
    }
    @Override
    public ResponseEntity<NewsDeleteResponse> deleteNews(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "newsId", required = true)
            String newsId ) {
        return ResponseEntity.ok(NewsDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(newsDeleteUseCase.deleteNews(newsId))
                .build());
    }

    @Override
    public ResponseEntity<NewsSearchResponse> searchNews(
            @ApiParam(value = "")
            @Valid
            @RequestBody NewsSearchRequest searchNewsRequest,
            BindingResult bindingResult1) {
        NewsSearchUseCaseOutput output = newsSearchUseCase.searchNews(this.buildNewsSearchUseCaseInput(searchNewsRequest));
        return ResponseEntity.ok(NewsSearchResponse.builder()
                .status(HttpStatus.OK.value())
                .results(NewsSearchResponseResults.builder()
                        .news(output.getNews().stream()
                                .map(this::buildNewsResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private NewsSearchUseCaseInput buildNewsSearchUseCaseInput(NewsSearchRequest request) {
        return NewsSearchUseCaseInput.builder()
                .title(request.getTitle())
                .userName(request.getUserName())
                .createDatetimeFrom(request.getCreateDatetimeFrom())
                .createDatetimeTo(request.getCreateDatetimeTo())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private NewsResponse buildNewsResponse(NewsSearchUseCaseResults results) {
        return NewsResponse.builder()
                .newsId(results.getNewsId())
                .title(results.getTitle())
                .content(results.getContent())
                .cover(results.getCover())
                .createUserId(results.getCreateUserId())
                .createUserName(results.getCreateUserName())
                .createDatetime(results.getCreateDatetime())
                .lastupUserId(results.getLastupUserId())
                .lastupUserName(results.getLastupUserName())
                .lastupDatetime(results.getLastupDatetime())
                .build();
    }

    @Override
    public ResponseEntity<NewsGetResponse> getNews(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "newsId", required = true)
            String newsId ) {
        NewsSearchUseCaseResults output = newsGetUseCase.getNews(newsId);
        return ResponseEntity.ok(NewsGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(Optional.ofNullable(output).isPresent()
                        ? List.of(this.buildNewsResponse(output))
                        : Collections.emptyList())
                .build());
    }
}

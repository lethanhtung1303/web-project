package com.tdtu.webproject.controller;

import generater.openapi.api.TestApi;
import generater.openapi.model.TestResponse;
import generater.openapi.model.TestResponseResults;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class TestApiController implements TestApi {

    @Override
    public ResponseEntity<TestResponse> test(){
        return ResponseEntity.ok(TestResponse.builder()
                .status(HttpStatus.OK.value())
                .results(TestResponseResults.builder()
                        .resultsTotalCount(1L)
                        .text("OK")
                        .build())
                .build());
    }
}
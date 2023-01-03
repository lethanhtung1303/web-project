package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.ContactCreateUseCase;
import com.tdtu.webproject.usecase.ContactHandleUseCase;
import generater.openapi.api.ContactApi;
import generater.openapi.model.ContactCreateRequest;
import generater.openapi.model.ContactCreateResponse;
import generater.openapi.model.ContactHandleRequest;
import generater.openapi.model.ContactHandleResponse;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class ContactApiController implements ContactApi {
    private final ContactCreateUseCase contactCreateUseCase;
    private final ContactHandleUseCase contactHandleUseCase;

    @Override
    public ResponseEntity<ContactCreateResponse> createContact(
            @ApiParam(value = "")
            @Valid
            @RequestBody ContactCreateRequest contactCreateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(ContactCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(contactCreateUseCase.createContact(
                        contactCreateRequest.getEmail(),
                        contactCreateRequest.getContent(),
                        contactCreateRequest.getPhone()))
                .build());
    }

    @Override
    public ResponseEntity<ContactHandleResponse> handleContact(
            @ApiParam(value = "")
            @Valid
            @RequestBody ContactHandleRequest contactHandleRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(ContactHandleResponse.builder()
                .status(HttpStatus.OK.value())
                .message(contactHandleUseCase.handleContact(
                        contactHandleRequest.getContactId(),
                        contactHandleRequest.getLastupUserId()))
                .build());
    }
}

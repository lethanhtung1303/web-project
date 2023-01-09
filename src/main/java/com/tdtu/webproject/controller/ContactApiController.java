package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import com.tdtu.webproject.usecase.ContactSearchUseCaseOutput.ContactSearchUseCaseResults;
import generater.openapi.api.ContactApi;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class ContactApiController implements ContactApi {
    private final ContactCreateUseCase contactCreateUseCase;
    private final ContactHandleUseCase contactHandleUseCase;
    private final ContactSearchUseCase contactSearchUseCase;
    private final ContactGetUseCase contactGetUseCase;
    private final ContactDeleteUseCase contactDeleteUseCase;

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

    @Override
    public ResponseEntity<ContactGetResponse> getContact(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "contactId", required = true)
            String contactId ) {
        ContactSearchUseCaseResults output = contactGetUseCase.getContact(contactId);
        return ResponseEntity.ok(ContactGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(Optional.ofNullable(output).isPresent()
                        ? List.of(this.buildContactResponse(output))
                        : Collections.emptyList())
                .build());
    }

    @Override
    public ResponseEntity<ContactSearchResponse> searchContact(
            @ApiParam(value = "")
            @Valid
            @RequestBody ContactSearchRequest contactSearchRequest,
            BindingResult bindingResult1) {
        ContactSearchUseCaseOutput output = contactSearchUseCase.searchContact(this.buildContactSearchUseCaseInput(contactSearchRequest));
        return ResponseEntity.ok(ContactSearchResponse.builder()
                .status(HttpStatus.OK.value())
                .results(ContactSearchResponseResults.builder()
                        .contact(output.getContact().stream()
                                .map(this::buildContactResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private ContactSearchUseCaseInput buildContactSearchUseCaseInput(ContactSearchRequest request) {
        return ContactSearchUseCaseInput.builder()
                .email(request.getEmail())
                .content(request.getContent())
                .phone(request.getPhone())
                .createDatetimeFrom(request.getCreateDatetimeFrom())
                .createDatetimeTo(request.getCreateDatetimeTo())
                .approveStatus(request.getApproveStatus())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private ContactResponse buildContactResponse(ContactSearchUseCaseResults results) {
        return ContactResponse.builder()
                .contactId(results.getContactId())
                .content(results.getContent())
                .email(results.getEmail())
                .phone(results.getPhone())
                .approveStatus(results.getApproveStatus())
                .handlerId(results.getHandlerId())
                .handlerName(results.getHandlerName())
                .createDatetime(results.getCreateDatetime())
                .lastupUserId(results.getLastupUserId())
                .lastupUserName(results.getLastupUserName())
                .lastupDatetime(results.getLastupDatetime())
                .build();
    }

    @Override
    public ResponseEntity<ContactDeleteResponse> deleteContact(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "contactId", required = true)
            String contactId ) throws Exception {
        return ResponseEntity.ok(ContactDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(contactDeleteUseCase.deleteContact(contactId))
                .build());
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.ContactSearchRequest;
import com.tdtu.webproject.model.ContactSearchResponse;
import com.tdtu.webproject.service.ContactService;
import com.tdtu.webproject.usecase.ContactSearchUseCaseOutput.ContactSearchUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactSearchUseCase {
    private final ContactService contactService;
    public ContactSearchUseCaseOutput searchContact(ContactSearchUseCaseInput input) {
        ContactSearchRequest request = this.buildContactSearchRequest(input);
        return ContactSearchUseCaseOutput.builder()
                .resultsTotalCount(contactService.count(request))
                .contact(contactService.search(request).stream()
                        .map(this::buildContactSearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private ContactSearchUseCaseResults buildContactSearchUseCaseResults(ContactSearchResponse response) {
        return ContactSearchUseCaseResults.builder()
                .contactId(response.getContactId())
                .content(response.getContent())
                .email(response.getEmail())
                .phone(response.getPhone())
                .approveStatus(response.getApproveStatus())
                .handlerId(response.getHandlerId())
                .handlerName(response.getHandlerName())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupUserName(response.getLastupUserName())
                .lastupDatetime(response.getLastupDatetime())
                .build();
    }

    private ContactSearchRequest buildContactSearchRequest(ContactSearchUseCaseInput input) {
        return ContactSearchRequest.builder()
                .email(input.getEmail())
                .content(input.getContent())
                .phone(input.getPhone())
                .createDatetimeFrom(input.getCreateDatetimeFrom())
                .createDatetimeTo(input.getCreateDatetimeTo())
                .approveStatus(input.getApproveStatus())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .build();
    }
}

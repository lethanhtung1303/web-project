package com.tdtu.webproject.usecase;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.ContactSearchRequest;
import com.tdtu.webproject.model.ContactSearchResponse;
import com.tdtu.webproject.service.ContactService;
import com.tdtu.webproject.usecase.ContactSearchUseCaseOutput.ContactSearchUseCaseResults;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactGetUseCase {
    private final ContactService contactService;
    public ContactSearchUseCaseResults getContact(String contactId) {
        List<ContactSearchResponse> contactList = contactService.search(ContactSearchRequest.builder()
                .contactId(contactId)
                .build());
        if (!ArrayUtil.isNotNullOrEmptyList(contactList)){
            throw new BusinessException("Not found contact!");
        }
        return contactList.stream()
                .filter(contact -> contact.getContactId().equals(contactId))
                .findFirst()
                .map(contact -> ContactSearchUseCaseResults.builder()
                        .contactId(contact.getContactId())
                        .content(contact.getContent())
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .approveStatus(contact.getApproveStatus())
                        .handlerId(contact.getHandlerId())
                        .handlerName(contact.getHandlerName())
                        .createDatetime(contact.getCreateDatetime())
                        .lastupUserId(contact.getLastupUserId())
                        .lastupUserName(contact.getLastupUserName())
                        .lastupDatetime(contact.getLastupDatetime())
                        .build())
                .orElse(null);
    }
}

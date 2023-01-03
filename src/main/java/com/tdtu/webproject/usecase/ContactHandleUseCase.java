package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactHandleUseCase {
    private final ContactService contactService;

    public String handleContact(String contactId, String lastupUserId) {
        return contactService.handleContact(contactId, lastupUserId);
    }
}

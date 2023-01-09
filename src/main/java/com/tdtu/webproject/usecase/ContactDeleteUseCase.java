package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactDeleteUseCase {
    private final ContactService contactService;
    public String deleteContact(String contactId) {
        return contactService.deleteContact(contactId);
    }
}

package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactCreateUseCase {
    private final ContactService contactService;
    public String createContact(String email, String content, String phone) {
        return contactService.createContact(email, content, phone);
    }
}

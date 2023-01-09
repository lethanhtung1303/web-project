package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import com.tdtu.webproject.repository.ContactRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactManageService {
    private final ContactRepository contactRepository;

    public boolean checkExistContact(BigDecimal contactId){
        List<TdtContact> results = this.getAllContact().stream()
                .filter(contact -> contact.getContactId().equals(contactId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public TdtContact getContact(BigDecimal contactId){
        return this.getAllContact().stream()
                .filter(contact -> contact.getContactId().equals(contactId))
                .findFirst()
                .orElse(null);
    }

    public List<TdtContact> getAllContact(){
        return contactRepository.selectAll();
    }
}

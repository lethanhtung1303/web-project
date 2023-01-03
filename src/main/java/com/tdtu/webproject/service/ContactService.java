package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.repository.ContactRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactManageService contactManageService;
    private final UserManageService userManageService;

    public String createContact(String email, String content, String phone) {
        return contactRepository.create(this.buildTdtContactForCreate(email, content, phone)) > 0
                ? Const.SUCCESSFUL
                : Const.FAIL;
    }

    private TdtContact buildTdtContactForCreate(String email, String content, String phone) {
        return TdtContact.builder()
                .email(email)
                .content(content)
                .phone(phone)
                .approveStatus(Const.UNAPPROVE)
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }

    public String handleContact(String contactId, String lastupUserId) {
        BigDecimal id = NumberUtil.toBigDeimal(contactId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            TdtContact contact = contactManageService.getContact(id);
            if (Optional.ofNullable(contact).isPresent()) {
                BigDecimal handlerId = NumberUtil.toBigDeimal(lastupUserId).orElse(null);
                if (Optional.ofNullable(handlerId).isPresent()) {
                    if (!userManageService.checkExistUser(handlerId)) {
                        throw new BusinessException("User handle not exists in the system!");
                    }
                    contact.setHandlerId(handlerId);
                    contact.setLastupUserId(lastupUserId);
                    contact.setLastupDatetime(DateUtil.getTimeNow());
                    if (contact.getApproveStatus().equals(Const.UNAPPROVE)) {
                        contact.setApproveStatus(Const.APPROVE);
                        return contactRepository.handle(contact) > 0
                                ? Const.SUCCESSFUL
                                : Const.FAIL;
                    }
                    contact.setApproveStatus(Const.UNAPPROVE);
                    return contactRepository.handle(contact) > 0
                            ? Const.SUCCESSFUL
                            : Const.FAIL;
                }
            }
        }
        return Const.FAIL;
    }
}

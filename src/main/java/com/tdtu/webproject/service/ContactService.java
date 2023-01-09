package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtContact;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.emun.ApproveStatus;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.ContactSearchCondition;
import com.tdtu.webproject.model.ContactSearchRequest;
import com.tdtu.webproject.model.ContactSearchResponse;
import com.tdtu.webproject.mybatis.result.ContactSearchResult;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.ContactRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Long count(ContactSearchRequest request) {
        return contactRepository.countContact(this.buildContactSearchCondition(request));
    }

    public List<ContactSearchResponse> search(ContactSearchRequest request) {
        List<ContactSearchResult> contactSearchResultList =
                contactRepository.searchContact(this.buildContactSearchCondition(request));
        return Optional.ofNullable(contactSearchResultList).isPresent()
                ? contactSearchResultList.stream()
                .map(this::buildContactSearchResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private ContactSearchCondition buildContactSearchCondition(ContactSearchRequest request) {
        String createDatetimeFrom = request.getCreateDatetimeFrom();
        String createDatetimeTo = request.getCreateDatetimeTo();
        String approveStatusCode = request.getApproveStatus();
        return ContactSearchCondition.builder()
                .contactId(NumberUtil.toBigDeimal(request.getContactId()).orElse(null))
                .email(request.getEmail())
                .phone(request.getPhone())
                .content(request.getContent())
                .approveStatus( Optional.ofNullable(approveStatusCode).isPresent()
                        ? this.generatorApproveStatus(approveStatusCode)
                        : null)
                .createDatetimeFrom(Optional.ofNullable(createDatetimeFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(
                        DateUtil.parseLocalDate(createDatetimeFrom, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .createDatetimeTo(Optional.ofNullable(createDatetimeTo).isPresent()
                        ? DateUtil.parseLocalDateTime(
                        DateUtil.parseLocalDate(createDatetimeTo, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private String generatorApproveStatus(String approveStatusCode){
        return ApproveStatus.getStatusByCode(Integer.parseInt(approveStatusCode));
    }

    private ContactSearchResponse buildContactSearchResponse(ContactSearchResult result) {
        List<UserSearchResult> userList = userManageService.getAllUserResult();
        BigDecimal handlerId = result.getHandlerId();
        LocalDateTime createDatetime = result.getCreateDatetime();
        BigDecimal lastupUserId = result.getLastupUserId();
        LocalDateTime lastupDatetime = result.getLastupDatetime();
        return ContactSearchResponse.builder()
                .contactId(StringUtil.convertBigDecimalToString(result.getContactId()))
                .content(result.getContent())
                .email(result.getEmail())
                .phone(result.getPhone())
                .approveStatus(result.getApproveStatus())
                .handlerId(StringUtil.convertBigDecimalToString(handlerId))
                .handlerName(Optional.ofNullable(handlerId).isPresent()
                        ? userManageService.getUserName(userList, handlerId)
                        : null)
                .createDatetime(Optional.ofNullable(createDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(createDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .lastupUserId(StringUtil.convertBigDecimalToString(lastupUserId))
                .lastupUserName(Optional.ofNullable(lastupUserId).isPresent()
                        ? userManageService.getUserName(userList, lastupUserId)
                        : null)
                .lastupDatetime(Optional.ofNullable(lastupDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lastupDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .build();
    }

    public String deleteContact(String contactId) {
        BigDecimal id = NumberUtil.toBigDeimal(contactId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            if (!contactManageService.checkExistContact(id)){
                throw new BusinessException("Not found Contact!");
            }
            return contactRepository.delete(id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }
}

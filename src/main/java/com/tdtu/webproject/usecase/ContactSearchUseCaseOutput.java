package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ContactSearchUseCaseOutput {
    private Long resultsTotalCount;
    private List<ContactSearchUseCaseResults> contact;
    @Data
    @Builder
    @AllArgsConstructor
    public static class ContactSearchUseCaseResults {
        private String contactId;
        private String content;
        private String email;
        private String phone;
        private String approveStatus;
        private String handlerId;
        private String handlerName;
        private String createDatetime;
        private String lastupUserId;
        private String lastupUserName;
        private String lastupDatetime;
    }
}

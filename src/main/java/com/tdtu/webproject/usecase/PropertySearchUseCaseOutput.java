package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PropertySearchUseCaseOutput {
    private Long resultsTotalCount;
    private List<PropertySearchUseCaseResults> properties;
    @Data
    @Builder
    @AllArgsConstructor
    public static class PropertySearchUseCaseResults{
        private String propertyId;
        private String typeId;
        private String typeName;
        private String createUserId;
        private String createUserName;
        private String createDatetime;
        private String lastupUserId;
        private String lastupUserName;
        private String lastupDatetime;
        private String title;
        private String address;
        private String amount;
        private String area;
        private String description;
        private String approveStatus;
        private List<String> propertyImg;
    }
}

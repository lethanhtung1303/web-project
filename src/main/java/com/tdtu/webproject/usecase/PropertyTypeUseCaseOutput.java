package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PropertyTypeUseCaseOutput {
    private Long resultsTotalCount;
    private List<PropertyTypeUseCaseResults> typeProperties;
    @Data
    @Builder
    @AllArgsConstructor
    public static class PropertyTypeUseCaseResults{
        private String nameProperty;
        private Long totalProperty;
    }
}

package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PropertySearchUseCaseInput {
    private String userId;
    private String typeId;
    private String title;
    private String address;
    private String createDatetimeFrom;
    private String createDatetimeTo;
    private String amountFrom;
    private String amountTo;
    private String areaFrom;
    private String areaTo;
    private Integer offset;
    private Integer limit;
}

package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PropertyCreateUseCaseInput {
    private String typeId;
    private String createUserId;
    private String title;
    private String address;
    private String amount;
    private String area;
    private String description;
    private List<String> propertyImg;
}

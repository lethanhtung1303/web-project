package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PropertyUpdateUseCaseInput {
    private String propertyId;
    private String typeId;
    private String lastupUserId;
    private String title;
    private String address;
    private String amount;
    private String area;
    private String description;
    private List<String> propertyImg;
}

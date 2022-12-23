package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PropertyTypeResponse {
    private String nameProperty;
    private Long totalProperty;
}

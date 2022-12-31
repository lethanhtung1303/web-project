package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StoreGetAllUseCaseOutput {
    private Long resultsTotalCount;
    private List<String> stores;
}

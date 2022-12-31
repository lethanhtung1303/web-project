package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class StoreGetAllUseCase {
    private final StoreService storeService;
    public StoreGetAllUseCaseOutput getAllStore(String userId) {
        return StoreGetAllUseCaseOutput.builder()
                .resultsTotalCount(storeService.count(userId))
                .stores(storeService.search(userId))
                .build();
    }
}

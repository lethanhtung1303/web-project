package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class StoreUpdateUseCase {
    private final StoreService storeService;
    public String updateStore(String propertyId, String createUserId) {
        return storeService.updateStore(propertyId, createUserId);
    }
}

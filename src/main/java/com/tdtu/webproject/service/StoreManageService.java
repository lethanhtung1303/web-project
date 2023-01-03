package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtStorage;
import com.tdtu.webproject.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreManageService {
    private final StoreRepository storeRepository;

    public TdtStorage getStorage(BigDecimal propertyId, BigDecimal userId){
        return this.getAllStore().stream()
                .filter(storage -> storage.getPropertyId().equals(propertyId)
                        && storage.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<TdtStorage> getAllStore(){
        return storeRepository.selectAll();
    }
}

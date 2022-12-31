package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtStorage;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.repository.StoreRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserManageService userManageService;
    private final PropertyManageService propertyManageService;
    private final StoreManageService storeManageService;

    public Long count(String userId) {
        BigDecimal id = NumberUtil.toBigDeimal(userId).orElse(null);
        if (Optional.ofNullable(id).isPresent()) {
            return storeRepository.count(id);
        }
        return 0L;
    }

    public List<String> search(String userId) {
        BigDecimal id = NumberUtil.toBigDeimal(userId).orElse(null);
        if (Optional.ofNullable(id).isPresent()) {
            List<TdtStorage> userSearchResultList = storeRepository.search(id);
            return Optional.ofNullable(userSearchResultList).isPresent()
                    ? userSearchResultList.stream()
                    .map(tdtStorage -> StringUtil.convertBigDecimalToString(tdtStorage.getPropertyId()))
                    .collect(Collectors.toList())
                    : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public String updateStore(String propertyId, String createUserId) {

        BigDecimal userId = NumberUtil.toBigDeimal(createUserId).orElse(null);
        if (Optional.ofNullable(userId).isPresent()){
            if (!userManageService.checkExistUser(userId)){
                throw new BusinessException("Not found user!");
            }
        }
        BigDecimal property = NumberUtil.toBigDeimal(propertyId).orElse(null);
        if (Optional.ofNullable(property).isPresent()){
            if (!propertyManageService.checkExistProperty(property)){
                throw new BusinessException("Not found property!");
            }
            TdtStorage storage = storeManageService.getStorage(property, userId);
            if (Optional.ofNullable(storage).isPresent())
            {
                if (storage.getIsDeleted()) {
                    storage.setIsDeleted(false);
                    return storeRepository.update(storage) > 0
                            ? Const.SUCCESSFUL
                            : Const.FAIL;
                }
                storage.setIsDeleted(true);
                return storeRepository.update(storage) > 0
                        ? Const.SUCCESSFUL
                        : Const.FAIL;
            }
            return  storeRepository.addStore(this.buildTdtStorageForAdd(property, userId)) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtStorage buildTdtStorageForAdd(BigDecimal propertyId, BigDecimal createUserId){
        return TdtStorage.builder()
                .propertyId(propertyId)
                .userId(createUserId)
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }
}

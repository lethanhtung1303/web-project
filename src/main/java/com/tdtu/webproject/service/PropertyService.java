package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;
import com.tdtu.webproject.model.PropertySearchCondition;
import com.tdtu.webproject.model.PropertySearchRequest;
import com.tdtu.webproject.model.PropertySearchResponse;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.PropertyImgRepository;
import com.tdtu.webproject.repository.PropertyRepository;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserManageService userManageService;
    private final PropertyImgRepository propertyImgRepository;

    public Long count(PropertySearchRequest request) {
        return propertyRepository.countUser(this.buildPropertySearchCondition(request));
    }

    public List<PropertySearchResponse> search(PropertySearchRequest request) {
        List<PropertySearchResult> propertySearchResult =
                propertyRepository.searchProperty(this.buildPropertySearchCondition(request));
        return Optional.ofNullable(propertySearchResult).isPresent()
                ? propertySearchResult.stream()
                    .map(this::buildPropertySearchResponse)
                    .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private PropertySearchCondition buildPropertySearchCondition(PropertySearchRequest request) {
        String createDatetimeFrom = request.getCreateDatetimeFrom();
        String createDatetimeTo = request.getCreateDatetimeTo();
        return PropertySearchCondition.builder()
                .userId(NumberUtil.toBigDeimal(request.getUserId()).orElse(null))
                .typeId(NumberUtil.toBigDeimal(request.getTypeId()).orElse(null))
                .title(request.getTitle())
                .address(request.getAddress())
                .createDatetimeFrom(Optional.ofNullable(createDatetimeFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(createDatetimeFrom, DateUtil.YYYMMDD_FORMAT_SLASH)
                        : null)
                .createDatetimeTo(Optional.ofNullable(createDatetimeTo).isPresent()
                        ? DateUtil.parseLocalDateTime(createDatetimeTo, DateUtil.YYYMMDD_FORMAT_SLASH)
                        : null)
                .amountFrom(NumberUtil.toBigDeimal(request.getAmountFrom()).orElse(null))
                .amountTo(NumberUtil.toBigDeimal(request.getAmountTo()).orElse(null))
                .areaFrom(NumberUtil.toBigDeimal(request.getAreaFrom()).orElse(null))
                .areaTo(NumberUtil.toBigDeimal(request.getAreaTo()).orElse(null))
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private PropertySearchResponse buildPropertySearchResponse(PropertySearchResult result) {
        List<TdtPropertyImg> propertyImgList = propertyImgRepository.selectAll();
        List<UserSearchResult> userList = userManageService.getAllUserResult();
        String createUserId = result.getCreateUserId();
        LocalDateTime createDatetime = result.getCreateDatetime();
        String lastupUserId = result.getLastupUserId();
        LocalDateTime lastupDatetime = result.getLastupDatetime();
        return PropertySearchResponse.builder()
                .propertyId(StringUtil.convertBigDecimalToString(result.getPropertyId()))
                .typeId(StringUtil.convertBigDecimalToString(result.getTypeId()))
                .typeName(result.getTypeName())
                .createUserId(createUserId)
                .createUserName(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserName(userList, NumberUtil.toBigDeimal(createUserId).orElse(null))
                        : null)
                .createDatetime(Optional.ofNullable(createDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(createDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .lastupUserId(lastupUserId)
                .lastupUserName(Optional.ofNullable(lastupUserId).isPresent()
                        ? userManageService.getUserName(userList, NumberUtil.toBigDeimal(lastupUserId).orElse(null))
                        : null)
                .lastupDatetime(Optional.ofNullable(lastupDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lastupDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .title(result.getTitle())
                .address(result.getAddress())
                .amount(StringUtil.convertBigDecimalToString(result.getAmount()))
                .area(StringUtil.convertBigDecimalToString(result.getArea()))
                .description(result.getDescription())
                .approveStatus(result.getApproveStatus())
                .propertyImg(Optional.ofNullable(propertyImgList).isPresent() ?
                        propertyImgList.stream()
                                .map(TdtPropertyImg::getPropertyImg)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}

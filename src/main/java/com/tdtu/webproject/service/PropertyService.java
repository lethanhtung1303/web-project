package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtProperty;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyImg;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtPropertyInfo;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.*;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.PropertyImgRepository;
import com.tdtu.webproject.repository.PropertyInfoRepository;
import com.tdtu.webproject.repository.PropertyRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final PropertyManageService propertyManageService;
    private final PropertyInfoRepository propertyInfoRepository;

    public Long count(PropertySearchRequest request) {
        PropertySearchCondition condition  = this.buildPropertySearchCondition(request);
        return Optional.ofNullable(condition).isPresent()
                ? propertyRepository.countProperty(condition)
                : 0L;
    }

    public List<PropertySearchResponse> search(PropertySearchRequest request) {
        PropertySearchCondition condition  = this.buildPropertySearchCondition(request);
        List<PropertySearchResult> propertySearchResult = Optional.ofNullable(condition).isPresent()
                ? propertyRepository.searchProperty(condition)
                : Collections.emptyList();
        return Optional.ofNullable(propertySearchResult).isPresent()
                ? propertySearchResult.stream()
                    .map(this::buildPropertySearchResponse)
                    .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private PropertySearchCondition buildPropertySearchCondition(PropertySearchRequest request) {
        List<String> userIdList = new ArrayList<>();
        if (Optional.ofNullable(request.getUserName()).isPresent()){
            userManageService.getUserIdByUserName(userManageService.getAllUserResult(), request.getUserName())
                    .forEach(userId -> userIdList.add(StringUtil.convertBigDecimalToString(userId)));
        }
        if (Optional.ofNullable(request.getUserName()).isPresent() && !ArrayUtil.isNotNullOrEmptyList(userIdList)){
            return null;
        }
        String createDatetimeFrom = request.getCreateDatetimeFrom();
        String createDatetimeTo = request.getCreateDatetimeTo();
        return PropertySearchCondition.builder()
                .propertyId(NumberUtil.toBigDeimal(request.getPropertyId()).orElse(null))
                .userId(request.getUserId())
                .userIdList(userIdList)
                .typeId(NumberUtil.toBigDeimal(request.getTypeId()).orElse(null))
                .title(request.getTitle())
                .address(request.getAddress())
                .createDatetimeFrom(Optional.ofNullable(createDatetimeFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(
                                DateUtil.parseLocalDate(createDatetimeFrom, DateUtil.DDMMYYYY_FORMAT_SLASH))
                        : null)
                .createDatetimeTo(Optional.ofNullable(createDatetimeTo).isPresent()
                        ? DateUtil.parseLocalDateTime(
                                DateUtil.parseLocalDate(createDatetimeTo, DateUtil.DDMMYYYY_FORMAT_SLASH))
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
        List<TdtPropertyImg> propertyImgList = propertyImgRepository.selectAll(result.getPropertyId());
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
                                .filter(img -> img.getPropertyId().equals(result.getPropertyId()))
                                .map(TdtPropertyImg::getPropertyImg)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public String createProperty(PropertyCreateRequest request) {
        BigDecimal propertyId = this.generatorPropertyId();
        BigDecimal typeId = NumberUtil.toBigDeimal(request.getTypeId()).orElse(null);
        if (Optional.ofNullable(typeId).isPresent()){
            if (!propertyManageService.checkExistPropertyType(typeId)){
                throw new BusinessException("Type Property not exists in the system!");
            }
        }
        List<String> propertyImg = request.getPropertyImg();
        if (ArrayUtil.isNotNullOrEmptyList(propertyImg) || propertyImg.size() < 3) {
            throw new BusinessException("Please select at least 3 images of the property!");
        }
        if (Optional.ofNullable(propertyId).isPresent()){
            if (propertyManageService.checkExistProperty(propertyId)){
                throw new BusinessException("Property already exists in the system!");
            }
            TdtProperty tdtProperty = this.buildTdtPropertyForCreate(propertyId, request);
            TdtPropertyInfo tdtPropertyInfo = this.buildTdtPropertyInfoForCreate(propertyId, request);
            List<TdtPropertyImg> tdtPropertyImgList = new ArrayList<>();
            request.getPropertyImg().forEach(img -> tdtPropertyImgList.add(this.buildTdtPropertyImg(propertyId, img)));
            return  propertyRepository.create(tdtProperty) > 0 
                    && propertyInfoRepository.create(tdtPropertyInfo) > 0
                    && this.createTdtPropertyImg(tdtPropertyImgList) == request.getPropertyImg().size()
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private int createTdtPropertyImg(List<TdtPropertyImg> tdtPropertyImgList) {
        int countCreate = 0;
        for(TdtPropertyImg img : tdtPropertyImgList) {
            countCreate += propertyImgRepository.create(img);
        }
        return countCreate;
    }

    private BigDecimal generatorPropertyId() {
        String propertyId = String.valueOf(DateUtil.getTimeNow().getYear()).substring(2)
                + DateUtil.getTimeNow().getMonthValue()
                + String.format("%03d", propertyRepository.countProperty(PropertySearchCondition.builder().build()) + 1);
        return NumberUtil.toBigDeimal(propertyId).orElse(null);
    }

    private TdtProperty buildTdtPropertyForCreate(BigDecimal propertyId, PropertyCreateRequest request){
        return TdtProperty.builder()
                .propertyId(propertyId)
                .typeId(NumberUtil.toBigDeimal(request.getTypeId()).orElse(BigDecimal.ONE))
                .createUserId(request.getCreateUserId())
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }
    private TdtPropertyInfo buildTdtPropertyInfoForCreate(BigDecimal propertyId, PropertyCreateRequest request){
        return TdtPropertyInfo.builder()
                .propertyId(propertyId)
                .title(request.getTitle())
                .address(request.getAddress())
                .amount(NumberUtil.toBigDeimal(request.getAmount()).orElse(BigDecimal.ZERO))
                .area(NumberUtil.toBigDeimal(request.getArea()).orElse(BigDecimal.ZERO))
                .description(request.getDescription())
                .approveStatus(Const.UNAPPROVE)
                .build();
    }

    private TdtPropertyImg buildTdtPropertyImg(BigDecimal propertyId, String propertyImg){
        return TdtPropertyImg.builder()
                .propertyId(propertyId)
                .propertyImg(propertyImg)
                .build();
    }

    public String updateProperty(PropertyUpdateRequest request) {
        BigDecimal propertyId = NumberUtil.toBigDeimal(request.getPropertyId()).orElse(null);
        List<String> propertyImgList = request.getPropertyImg();
        if (!ArrayUtil.isNotNullOrEmptyList(propertyImgList) || propertyImgList.size() < 3) {
            throw new BusinessException("Please select at least 3 images of the property!");
        }
        BigDecimal typeId = NumberUtil.toBigDeimal(request.getTypeId()).orElse(null);
        if (Optional.ofNullable(typeId).isPresent()){
            if (!propertyManageService.checkExistPropertyType(typeId)){
                throw new BusinessException("Type Property not exists in the system!");
            }
        }
        if (Optional.ofNullable(propertyId).isPresent()){
            if (!propertyManageService.checkExistProperty(propertyId)){
                throw new BusinessException("Not found property!");
            }
            List<TdtPropertyImg> tdtPropertyImgList = new ArrayList<>();
            request.getPropertyImg().forEach(img -> tdtPropertyImgList.add(this.buildTdtPropertyImg(propertyId,  img)));
            return propertyRepository.update(this.buildTdtPropertyForUpdate(typeId, request.getLastupUserId()), propertyId) > 0
                    && propertyInfoRepository.update(this.buildTdtPropertyInfoForUpdate(request), propertyId) > 0
                    && this.updateTdtPropertyImg(tdtPropertyImgList, propertyId) == request.getPropertyImg().size()
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtProperty buildTdtPropertyForUpdate(BigDecimal typeId, String lastupUserId){
        return TdtProperty.builder()
                .typeId(typeId)
                .lastupUserId(lastupUserId)
                .lastupDatetime(DateUtil.getTimeNow())
                .build();
    }

    private TdtPropertyInfo buildTdtPropertyInfoForUpdate(PropertyUpdateRequest request){
        return TdtPropertyInfo.builder()
                .title(request.getTitle())
                .address(request.getAddress())
                .amount(NumberUtil.toBigDeimal(request.getAmount()).orElse(null))
                .area(NumberUtil.toBigDeimal(request.getArea()).orElse(null))
                .description(request.getDescription())
                .build();
    }

    private int updateTdtPropertyImg(List<TdtPropertyImg> tdtPropertyImgList, BigDecimal propertyId) {
        int countUpdate = 0;
        if (propertyImgRepository.delete(propertyId) > 0) {
           return this.createTdtPropertyImg(tdtPropertyImgList);
        }
        return countUpdate;
    }

    public String deleteProperty(String propertyId) {
        BigDecimal id = NumberUtil.toBigDeimal(propertyId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            if (!propertyManageService.checkExistProperty(id)){
                throw new BusinessException("Not found property!");
            }
            return propertyRepository.delete(id) > 0
                    && propertyInfoRepository.delete(id) > 0
                    && propertyImgRepository.delete(id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }
}

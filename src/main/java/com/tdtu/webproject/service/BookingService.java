package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtBooking;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.emun.ApproveStatus;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.BookingCreateRequest;
import com.tdtu.webproject.model.BookingSearchCondition;
import com.tdtu.webproject.model.BookingSearchRequest;
import com.tdtu.webproject.model.BookingSearchResponse;
import com.tdtu.webproject.mybatis.result.BookingSearchResult;
import com.tdtu.webproject.mybatis.result.PropertySearchResult;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.BookingRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {
    private final PropertyManageService propertyManageService;
    private final UserManageService userManageService;
    private final BookingManageService bookingManageService;
    private final BookingRepository bookingRepository;
    public String createBooking(BookingCreateRequest request) {
        BigDecimal propertyId = NumberUtil.toBigDeimal(request.getPropertyId()).orElse(null);
        String bookingDate = request.getBookingDate();
        LocalDateTime dateTimeBooking = Optional.ofNullable(bookingDate).isPresent()
                ? DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(bookingDate, DateUtil.YYYYMMDD_FORMAT_SLASH))
                : null;

        if (Optional.ofNullable(propertyId).isEmpty() || Optional.ofNullable(dateTimeBooking).isEmpty()){
            return Const.FAIL;
        }

        if (!propertyManageService.checkExistProperty(propertyId)){
            throw new BusinessException("Not found property!");
        }

        if (!DateUtil.isValidDateTime(dateTimeBooking)){
            throw new BusinessException("Time booking not valid!");
        }

        TdtBooking record = TdtBooking.builder()
                .propertyId(propertyId)
                .bookingDate(dateTimeBooking)
                .note(request.getNote())
                .approveStatus(ApproveStatus.UNAPPROVED.name())
                .createUserId(request.getCreateUserId())
                .createDatetime(DateUtil.getTimeNow())
                .build();
        return  bookingRepository.create(record) > 0
                ? Const.SUCCESSFUL
                : Const.FAIL;
    }

    public Long count(BookingSearchRequest request) {
        BookingSearchCondition condition  = this.buildBookingSearchCondition(request);
        if ((Optional.ofNullable(request.getPropertyOwnerId()).isPresent()
                || Optional.ofNullable(request.getPropertyTitle()).isPresent())
                && !ArrayUtil.isNotNullOrEmptyList(condition.getPropertyIdList())){
            return 0L;
        }
        return Optional.ofNullable(condition).isPresent()
                ? bookingRepository.countBooking(condition)
                : 0L;
    }

    private BookingSearchCondition buildBookingSearchCondition(BookingSearchRequest request) {
        String bookingDateFrom = request.getBookingDateFrom();
        String bookingDateTo = request.getBookingDateTo();
        String createDatetimeFrom = request.getCreateDatetimeFrom();
        String createDatetimeTo = request.getCreateDatetimeTo();
        String approveStatusCode = request.getApproveStatus();
        List<BigDecimal> propertyIdList = this.getPropertyId(request.getPropertyOwnerId(), request.getPropertyTitle());
        return BookingSearchCondition.builder()
                .bookingId(NumberUtil.toBigDeimal(request.getBookingId()).orElse(null))
                .propertyIdList(propertyIdList)
                .bookingDateFrom(Optional.ofNullable(bookingDateFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(bookingDateFrom, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .bookingDateTo(Optional.ofNullable(bookingDateTo).isPresent()
                        ? DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(bookingDateTo, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .createUserId(request.getCreateUserId())
                .createDatetimeFrom(Optional.ofNullable(createDatetimeFrom).isPresent()
                        ? DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(createDatetimeFrom, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .createDatetimeTo(Optional.ofNullable(createDatetimeTo).isPresent()
                        ? DateUtil.parseLocalDateTime(DateUtil.parseLocalDate(createDatetimeTo, DateUtil.YYYYMMDD_FORMAT_SLASH))
                        : null)
                .approveStatus( Optional.ofNullable(approveStatusCode).isPresent()
                        ? this.generatorApproveStatus(approveStatusCode)
                        : null)
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private List<BigDecimal> getPropertyId(String propertyOwnerId, String propertyTitle){
        List<PropertySearchResult> propertyList = propertyManageService.getAllPropertySearchResult();
        if (Optional.ofNullable(propertyOwnerId).isPresent()){
            return propertyList.stream()
                    .filter(item -> item.getCreateUserId().equals(propertyOwnerId))
                    .map(PropertySearchResult::getPropertyId)
                    .collect(Collectors.toList());
        }
        if (Optional.ofNullable(propertyTitle).isPresent()){
            return propertyList.stream()
                    .filter(item -> item.getTitle().contains(propertyTitle))
                    .map(PropertySearchResult::getPropertyId)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private String generatorApproveStatus(String approveStatusCode){
        return ApproveStatus.getStatusByCode(Integer.parseInt(approveStatusCode));
    }

    public List<BookingSearchResponse> search(BookingSearchRequest request) {
        BookingSearchCondition condition  = this.buildBookingSearchCondition(request);
        if ((Optional.ofNullable(request.getPropertyOwnerId()).isPresent()
                || Optional.ofNullable(request.getPropertyTitle()).isPresent())
                && !ArrayUtil.isNotNullOrEmptyList(condition.getPropertyIdList())){
            return Collections.emptyList();
        }
        List<BookingSearchResult> bookingSearchResultList = bookingRepository.searchBooking(condition);
        return Optional.ofNullable(bookingSearchResultList).isPresent()
                ? bookingSearchResultList.stream()
                .map(this::buildBookingSearchResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private BookingSearchResponse buildBookingSearchResponse(BookingSearchResult result) {
        BigDecimal propertyId = result.getPropertyId();
        PropertySearchResult property = propertyManageService.getPropertySearchResultById(propertyId);
        BigDecimal userId = NumberUtil.toBigDeimal(property.getCreateUserId()).orElse(null);
        List<UserSearchResult> userList = userManageService.getAllUserResult();
        LocalDateTime bookingDate = result.getBookingDate();
        BigDecimal createUserId = NumberUtil.toBigDeimal(result.getCreateUserId()).orElse(null);
        LocalDateTime createDatetime = result.getCreateDatetime();
        BigDecimal lastupUserId = NumberUtil.toBigDeimal(result.getLastupUserId()).orElse(null);
        LocalDateTime lastupDatetime = result.getLastupDatetime();
        return BookingSearchResponse.builder()
                .bookingId(StringUtil.convertBigDecimalToString(result.getBookingId()))
                .propertyId(StringUtil.convertBigDecimalToString(propertyId))
                .propertyTitle(property.getTitle())
                .propertyOwnerId(property.getCreateUserId())
                .propertyOwnerName(Optional.ofNullable(userId).isPresent()
                        ? userManageService.getUserName(userList, userId)
                        : null)
                .propertyOwnerPhone(Optional.ofNullable(userId).isPresent()
                        ? userManageService.getUserPhone(userList, userId)
                        : null)
                .propertyOwnerEmail(Optional.ofNullable(userId).isPresent()
                        ? userManageService.getUserEmail(userList, userId)
                        : null)
                .bookingDate(Optional.ofNullable(bookingDate).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(bookingDate, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .note(result.getNote())
                .approveStatus(result.getApproveStatus())
                .createUserId(result.getCreateUserId())
                .createUserName(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserName(userList, createUserId)
                        : null)
                .bookingUserPhone(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserPhone(userList, createUserId)
                        : null)
                .bookingUserEmail(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserEmail(userList, createUserId)
                        : null)
                .createDatetime(Optional.ofNullable(createDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(createDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .lastupUserId(result.getLastupUserId())
                .lastupUserName(Optional.ofNullable(lastupUserId).isPresent()
                        ? userManageService.getUserName(userList, lastupUserId)
                        : null)
                .lastupDatetime(Optional.ofNullable(lastupDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lastupDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .build();
    }

    public String updateBooking(String bookingId, String approveStatus, String lastupUserId) {
        BigDecimal id = NumberUtil.toBigDeimal(bookingId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            if (!bookingManageService.checkExistBooking(id)){
                throw new BusinessException("Not found booking!");
            }
            return bookingRepository.update(this.buildTdtBookingForUpdate(approveStatus, lastupUserId), id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtBooking buildTdtBookingForUpdate(String approveStatus, String lastupUserId) {
        return TdtBooking.builder()
                .approveStatus(this.generatorApproveStatus(approveStatus))
                .lastupUserId(lastupUserId)
                .lastupDatetime(DateUtil.getTimeNow())
                .build();
    }

    public String deleteBooking(String bookingId) {
        BigDecimal id = NumberUtil.toBigDeimal(bookingId).orElse(null);
        if (Optional.ofNullable(id).isPresent()){
            if (!bookingManageService.checkExistBooking(id)){
                throw new BusinessException("Not found booking!");
            }
            return bookingRepository.delete(id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }
}

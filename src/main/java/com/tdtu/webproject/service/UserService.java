package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserInfo;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.*;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.UserInfoRepository;
import com.tdtu.webproject.repository.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserManageService userManageService;

    public Long count(UserSearchRequest request) {
        return userRepository.countUser(this.buildUserSearchCondition(request));
    }

    public List<UserSearchResponse> search(UserSearchRequest request) {
        List<UserSearchResult> userSearchResultList = userRepository.searchUser(this.buildUserSearchCondition(request));
        return Optional.ofNullable(userSearchResultList).isPresent()
                ? userSearchResultList.stream()
                    .map(this::buildUserSearchResponse)
                    .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private UserSearchCondition buildUserSearchCondition(UserSearchRequest request) {
        return UserSearchCondition.builder()
                .userId(NumberUtil.toBigDeimal(request.getUserId()).orElse(null))
                .email(request.getEmail())
                .stdId(request.getStdId())
                .userName(request.getUserName())
                .address(request.getAddress())
                .idCard(request.getIdCard())
                .phone(request.getPhone())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private UserSearchResponse buildUserSearchResponse(UserSearchResult result) {
        List<UserSearchResult> userList = userManageService.getAllUser();
        BigDecimal createUserId = result.getCreateUserId();
        LocalDateTime createDatetime = result.getCreateDatetime();
        BigDecimal lastupUserId = result.getLastupUserId();
        LocalDateTime lastupDatetime = result.getLastupDatetime();
        return UserSearchResponse.builder()
                .userId(StringUtil.convertBigDecimalToString(result.getUserId()))
                .userName(result.getUserName())
                .approveStatus(result.getApproveStatus())
                .stdId(result.getStdId())
                .address(result.getAddress())
                .idCard(result.getIdCard())
                .fCard(result.getFCard())
                .bCard(result.getBCard())
                .portrait(result.getPortrait())
                .phone(result.getPhone())
                .email(result.getEmail())
                .password(result.getPassword())
                .roleId(StringUtil.convertBigDecimalToString(result.getRoleId()))
                .roleName(result.getRoleName())
                .createUserId(StringUtil.convertBigDecimalToString(createUserId))
                .createUserName(Optional.ofNullable(createUserId).isPresent()
                        ? userManageService.getUserName(userList, createUserId)
                        : null)
                .createDatetime(Optional.ofNullable(createDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(createDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .lastupUserId(StringUtil.convertBigDecimalToString(lastupUserId))
                .lastupUserName(Optional.ofNullable(lastupUserId).isPresent()
                        ? userManageService.getUserName(userList, lastupUserId)
                        : null)
                .lastupDatetime(Optional.ofNullable(lastupDatetime).isPresent()
                        ? DateUtil.getValueFromLocalDateTime(lastupDatetime, DateUtil.DATETIME_FORMAT_SLASH)
                        : null)
                .build();
    }

    public String updateUser(UserUpdateRequest request) {
        BigDecimal userId = NumberUtil.toBigDeimal(request.getUserId()).orElse(null);
        String stdId = request.getStdId();
        if (StringUtil.isNotNullOrEmptyString(stdId)
                && Optional.ofNullable(userId).isPresent()){
            if (userManageService.validateStdId(userId, stdId)){
                throw new BusinessException("Student ID already exists in the system!");
            }
        }
        String idCard = request.getIdCard();
        if (StringUtil.isNotNullOrEmptyString(idCard)){
            if (userManageService.validateIdCard(userId, idCard)){
                throw new BusinessException("ID card already exists in the system!");
            }
        }

        if (Optional.ofNullable(userId).isPresent()){
            if (!userManageService.checkExistUser(userId)){
                throw new BusinessException("Not found user!");
            }
            return userInfoRepository.update(this.buildTdtUserInfo(userId, request), userId) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtUserInfo buildTdtUserInfo(BigDecimal userId, UserUpdateRequest request){
        TdtUserInfo userInfo = userInfoRepository.getUserInfo(userId);
        return Optional.ofNullable(userInfo)
                .map(i -> TdtUserInfo.builder()
                        .userInfoId(i.getUserInfoId())
                        .userId(userId)
                        .stdId(request.getStdId())
                        .name(request.getUserName())
                        .address(request.getAddress())
                        .idCard(request.getIdCard())
                        .fCard(request.getFCard())
                        .bCard(request.getBCard())
                        .portrait(request.getPortrait())
                        .phone(i.getPhone())
                        .isDeleted(i.getIsDeleted())
                        .lastupUserId(request.getLastupUserId())
                        .lastupDatetime(DateUtil.getTimeNow())
                        .build())
                .orElse(null);
    }

    public String createUser(UserCreateRequest request) {
        BigDecimal userId = this.generatorUserId();
        String stdId = request.getStdId();
        if (StringUtil.isNotNullOrEmptyString(stdId)
                && Optional.ofNullable(userId).isPresent()){
            if (userManageService.validateStdId(userId, stdId)){
                throw new BusinessException("Student ID already exists in the system!");
            }
        }
        String idCard = request.getIdCard();
        if (StringUtil.isNotNullOrEmptyString(idCard)
                && Optional.ofNullable(userId).isPresent()){
            if (userManageService.validateIdCard(userId, idCard)){
                throw new BusinessException("ID card already exists in the system!");
            }
        }
        String phone = request.getPhone();
        if (StringUtil.isNotNullOrEmptyString(phone)
                && Optional.ofNullable(userId).isPresent()){
            if (userManageService.validatePhone(userId, phone)){
                throw new BusinessException("Phone number already exists in the system!");
            }
        }
        if (Optional.ofNullable(userId).isPresent()){
            if (userManageService.checkExistUser(userId)){
                throw new BusinessException("Not found user!");
            }
            TdtUser tdtUser = this.buildTdtUserForCreate(userId, request);
            TdtUserInfo tdtUserInfo = this.buildTdtUserInfoForCreate(userId, request);
            return userRepository.create(tdtUser) > 0 && userInfoRepository.create(tdtUserInfo) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private BigDecimal generatorUserId() {
        String userId = String.valueOf(DateUtil.getTimeNow().getYear()).substring(2)
                + DateUtil.getTimeNow().getMonthValue()
                + String.format("%03d", userRepository.countUser(UserSearchCondition.builder().build()) + 1);
        return NumberUtil.toBigDeimal(userId).orElse(null);
    }

    private TdtUserInfo buildTdtUserInfoForCreate(BigDecimal userId, UserCreateRequest request){
        return TdtUserInfo.builder()
                .userInfoId(userId)
                .userId(userId)
                .stdId(request.getStdId())
                .name(request.getUserName())
                .address(request.getAddress())
                .idCard(request.getIdCard())
                .fCard(request.getFCard())
                .bCard(request.getBCard())
                .portrait(request.getPortrait())
                .phone(request.getPhone())
                .isDeleted(false)
                .build();
    }

    private TdtUser buildTdtUserForCreate(BigDecimal userId, UserCreateRequest request){
        return TdtUser.builder()
                .userId(userId)
                .email(request.getEmail())
                .password(request.getPassword())
                .roleId(NumberUtil.toBigDeimal(request.getRoleId()).orElse(null))
                .approveStatus(Const.UNAPPROVE)
                .isDeleted(false)
                .createUserId(request.getCreateUserId())
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }
}

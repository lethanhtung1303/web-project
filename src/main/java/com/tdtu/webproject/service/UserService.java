package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserInfo;
import com.tdtu.webproject.constant.Const;
import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.*;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.UserInfoRepository;
import com.tdtu.webproject.repository.UserRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import com.tdtu.webproject.utils.DateUtil;
import com.tdtu.webproject.utils.NumberUtil;
import com.tdtu.webproject.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

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
        List<UserSearchResult> userList = userManageService.getAllUserResult();
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
                throw new BusinessException("User already exists in the system!");
            }
            TdtUser tdtUser = this.buildTdtUserForCreate(userId, request);
            TdtUserInfo tdtUserInfo = this.buildTdtUserInfoForCreate(userId, request);
            return (userRepository.create(tdtUser) > 0 && userInfoRepository.create(tdtUserInfo) > 0)
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
                .password(this.encodePassword(request.getPassword()))
                .roleId(NumberUtil.toBigDeimal(request.getRoleId()).orElse(null))
                .approveStatus(Const.UNAPPROVE)
                .isDeleted(false)
                .createUserId(request.getCreateUserId())
                .createDatetime(DateUtil.getTimeNow())
                .build();
    }

    private String encodePassword(String pass){
        return encoder.encode(pass);
    }

    public String deleteUser(String userId) {
        BigDecimal id = NumberUtil.toBigDeimal(userId).orElse(null);
        if (Optional.ofNullable(userId).isPresent()){
            if (!userManageService.checkExistUser(id) && !userManageService.checkExistUserInfo(id)){
                throw new BusinessException("Not found user!");
            }
            return userRepository.delete(id) > 0 && userInfoRepository.delete(id) > 0
                    ? Const.SUCCESSFUL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    public String changePassword(ChangePassRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())){
            throw new BusinessException("Password and Password Confirm don't match");
        }
        BigDecimal userId = NumberUtil.toBigDeimal(request.getUserId()).orElse(null);
        if (Optional.ofNullable(userId).isPresent()){
            if (!userManageService.checkExistUser(userId)){
                throw new BusinessException("Not found user!");
            }
            List<TdtUser> userList = userManageService.getAllUser();
            TdtUser userOld = ArrayUtil.isNotNullOrEmptyList(userList)
                    ? userManageService.getAllUser().stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null)
                    : null;
            if (Optional.ofNullable(userOld).isPresent()){
                if (!encoder.matches(request.getPasswordOld(), userOld.getPassword())){
                    throw new BusinessException("Password don't match");
                }
            }
            TdtUser user = this.buildTdtUser(userId, this.encodePassword(request.getPassword()),
                    request.getLastupUserId());
            return Optional.ofNullable(user).isPresent()
                    ? userRepository.changePassword(user, userId) > 0
                        ? Const.SUCCESSFUL
                        : Const.FAIL
                    : Const.FAIL;
        }
        return Const.FAIL;
    }

    private TdtUser buildTdtUser(BigDecimal userId, String password, String lastupUserId){
        TdtUser user = userRepository.getUser(userId);
        return Optional.ofNullable(user)
                .map(i -> TdtUser.builder()
                        .userId(i.getUserId())
                        .email(i.getEmail())
                        .password(password)
                        .roleId(i.getRoleId())
                        .approveStatus(i.getApproveStatus())
                        .isDeleted(i.getIsDeleted())
                        .createUserId(i.getCreateUserId())
                        .createDatetime(i.getCreateDatetime())
                        .lastupUserId(lastupUserId)
                        .lastupDatetime(DateUtil.getTimeNow())
                        .build())
                .orElse(null);
    }

    public String login(String email, String password) {
        List<TdtUser> userList = userManageService.getAllUser();
        TdtUser user = ArrayUtil.isNotNullOrEmptyList(userList)
                ? userManageService.getAllUser().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst()
                    .orElse(null)
                : null;
        if (Optional.ofNullable(user).isEmpty()){
            throw new BusinessException(Const.FAIL);
        }
        if (!encoder.matches(password, user.getPassword())){
            throw new BusinessException("Password don't match");
        }
        return Const.SUCCESSFUL;
    }
}

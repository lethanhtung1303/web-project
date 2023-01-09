package com.tdtu.webproject.service;

import com.tdtu.mbGenerator.generate.mybatis.model.TdtUser;
import com.tdtu.mbGenerator.generate.mybatis.model.TdtUserInfo;
import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.UserInfoRepository;
import com.tdtu.webproject.repository.UserRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManageService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    public boolean checkExistUser(BigDecimal userId){
        List<TdtUser> results = this.getAllUser().stream()
                .filter(user -> user.getUserId().equals(userId)).toList();
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public boolean checkExistUserInfo(BigDecimal userId){
        TdtUserInfo results = userInfoRepository.getUserInfo(userId);
        return Optional.ofNullable(results).isPresent();
    }

    public boolean validateStdId(BigDecimal userId, String stdId){
        List<UserSearchResult> results = userRepository.searchUser(
                UserSearchCondition.builder()
                        .stdId(stdId)
                        .build()).stream()
                .filter(user -> !user.getUserId().equals(userId))
                .collect(Collectors.toList());
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public boolean validateIdCard(BigDecimal userId, String idCard){
        List<UserSearchResult> results = userRepository.searchUser(
                UserSearchCondition.builder()
                        .idCard(idCard)
                        .build()).stream()
                .filter(user -> !user.getUserId().equals(userId))
                .collect(Collectors.toList());
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public boolean validatePhone(BigDecimal userId, String phone){
        List<UserSearchResult> results = userRepository.searchUser(
                UserSearchCondition.builder()
                        .phone(phone)
                        .build()).stream()
                .filter(user -> !user.getUserId().equals(userId))
                .collect(Collectors.toList());
        return ArrayUtil.isNotNullOrEmptyList(results);
    }

    public List<UserSearchResult> getAllUserResult(){
        return userRepository.searchUser(UserSearchCondition.builder().build());
    }

    public List<TdtUser> getAllUser(){
        return userRepository.selectAll();
    }

    public String getUserName(List<UserSearchResult> userList, BigDecimal userId){
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(UserSearchResult::getUserName)
                .orElse(null);
    }

    public String getUserPhone(List<UserSearchResult> userList, BigDecimal userId){
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(UserSearchResult::getPhone)
                .orElse(null);
    }

    public String getUserEmail(List<UserSearchResult> userList, BigDecimal userId){
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(UserSearchResult::getEmail)
                .orElse(null);
    }

    public List<BigDecimal> getUserIdByUserName(List<UserSearchResult> userList, String userName){
        return userList.stream()
                .filter(user -> user.getUserName().contains(userName))
                .map(UserSearchResult::getUserId)
                .collect(Collectors.toList());
    }
}

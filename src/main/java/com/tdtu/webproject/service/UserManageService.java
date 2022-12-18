package com.tdtu.webproject.service;

import com.tdtu.webproject.model.UserSearchCondition;
import com.tdtu.webproject.mybatis.result.UserSearchResult;
import com.tdtu.webproject.repository.UserRepository;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManageService {
    private final UserRepository userRepository;

    public boolean checkExistUser(BigDecimal userId){
        List<UserSearchResult> results = userRepository.searchUser(
                UserSearchCondition.builder()
                        .userId(userId)
                        .build());
        return ArrayUtil.isNotNullOrEmptyList(results);
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

    public List<UserSearchResult> getAllUser(){
        return userRepository.searchUser(UserSearchCondition.builder().build());
    }

    public String getUserName(List<UserSearchResult> userList, BigDecimal userId){
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(UserSearchResult::getUserName)
                .orElse(null);
    }
}

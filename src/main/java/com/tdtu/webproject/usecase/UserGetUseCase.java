package com.tdtu.webproject.usecase;

import com.tdtu.webproject.exception.BusinessException;
import com.tdtu.webproject.model.UserSearchRequest;
import com.tdtu.webproject.model.UserSearchResponse;
import com.tdtu.webproject.service.UserService;
import com.tdtu.webproject.usecase.UserSearchUseCaseOutput.UserSearchUseCaseResults;
import com.tdtu.webproject.utils.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserGetUseCase {

    private final UserService userService;

    public UserSearchUseCaseResults getUser(String userId) {
        List<UserSearchResponse> userList = userService.search(UserSearchRequest.builder()
                .userId(userId)
                .build());
        if (!ArrayUtil.isNotNullOrEmptyList(userList)){
            throw new BusinessException("Not found user!");
        }
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(user -> UserSearchUseCaseResults.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .approveStatus(user.getApproveStatus())
                        .stdId(user.getStdId())
                        .address(user.getAddress())
                        .idCard(user.getIdCard())
                        .fCard(user.getFCard())
                        .bCard(user.getBCard())
                        .portrait(user.getPortrait())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .roleId(user.getRoleId())
                        .roleName(user.getRoleName())
                        .createUserId(user.getCreateUserId())
                        .createUserName(user.getCreateUserName())
                        .createDatetime(user.getCreateDatetime())
                        .lastupUserId(user.getLastupUserId())
                        .lastupUserName(user.getLastupUserName())
                        .lastupDatetime(user.getLastupDatetime())
                        .build())
                .orElse(UserSearchUseCaseResults.builder().build());
    }
}

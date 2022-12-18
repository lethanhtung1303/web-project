package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.UserSearchRequest;
import com.tdtu.webproject.model.UserSearchResponse;
import com.tdtu.webproject.service.UserService;
import com.tdtu.webproject.usecase.UserSearchUseCaseOutput.UserSearchUseCaseResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserSearchUseCase {

    private final UserService userService;

    public UserSearchUseCaseOutput searchUser(UserSearchUseCaseInput input) {
        UserSearchRequest request = this.buildUserSearchRequest(input);
        return UserSearchUseCaseOutput.builder()
                .resultsTotalCount(userService.count(request))
                .users(userService.search(request).stream()
                        .map(this::buildUserSearchUseCaseResults)
                        .collect(Collectors.toList()))
                .build();
    }

    private UserSearchRequest buildUserSearchRequest(UserSearchUseCaseInput input) {
        return UserSearchRequest.builder()
                .email(input.getEmail())
                .stdId(input.getStdId())
                .userName(input.getUserName())
                .address(input.getAddress())
                .idCard(input.getIdCard())
                .phone(input.getPhone())
                .offset(input.getOffset())
                .limit(input.getLimit())
                .build();
    }

    private UserSearchUseCaseResults buildUserSearchUseCaseResults(UserSearchResponse response) {
        return UserSearchUseCaseResults.builder()
                .userId(response.getUserId())
                .userName(response.getUserName())
                .approveStatus(response.getApproveStatus())
                .stdId(response.getStdId())
                .address(response.getAddress())
                .idCard(response.getIdCard())
                .fCard(response.getFCard())
                .bCard(response.getBCard())
                .portrait(response.getPortrait())
                .phone(response.getPhone())
                .email(response.getEmail())
                .password(response.getPassword())
                .roleId(response.getRoleId())
                .roleName(response.getRoleName())
                .createUserId(response.getCreateUserId())
                .createUserName(response.getCreateUserName())
                .createDatetime(response.getCreateDatetime())
                .lastupUserId(response.getLastupUserId())
                .lastupUserName(response.getLastupUserName())
                .lastupDatetime(response.getLastupDatetime())
                .build();
    }
}

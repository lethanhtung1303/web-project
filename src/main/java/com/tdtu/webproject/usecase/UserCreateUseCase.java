package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.UserCreateRequest;
import com.tdtu.webproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserCreateUseCase {

    private final UserService userService;

    public String createUser(UserCreateUseCaseInput input) {
        UserCreateRequest request = this.buildUserCreateRequest(input);
        return userService.createUser(request);
    }

    private UserCreateRequest buildUserCreateRequest(UserCreateUseCaseInput input) {
        return UserCreateRequest.builder()
                .email(input.getEmail())
                .password(input.getPassword())
                .roleId(input.getRoleId())
                .createUserId(input.getCreateUserId())
                .stdId(input.getStdId())
                .userName(input.getUserName())
                .address(input.getAddress())
                .idCard(input.getIdCard())
                .fCard(input.getFCard())
                .bCard(input.getBCard())
                .portrait(input.getPortrait())
                .phone(input.getPhone())
                .build();
    }
}

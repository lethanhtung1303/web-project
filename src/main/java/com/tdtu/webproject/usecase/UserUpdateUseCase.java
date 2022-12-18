package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.UserUpdateRequest;
import com.tdtu.webproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserUpdateUseCase {

    private final UserService userService;

    public String updateUser(UserUpdateUseCaseInput input) {
        UserUpdateRequest request = this.buildUserSearchRequest(input);
        return userService.updateUser(request);
    }

    private UserUpdateRequest buildUserSearchRequest(UserUpdateUseCaseInput input) {
        return UserUpdateRequest.builder()
                .userId(input.getUserId())
                .stdId(input.getStdId())
                .userName(input.getUserName())
                .address(input.getAddress())
                .idCard(input.getIdCard())
                .fCard(input.getFCard())
                .bCard(input.getBCard())
                .portrait(input.getPortrait())
                .lastupUserId(input.getLastupUserId())
                .build();
    }
}

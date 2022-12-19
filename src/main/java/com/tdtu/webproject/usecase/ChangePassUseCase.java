package com.tdtu.webproject.usecase;

import com.tdtu.webproject.model.ChangePassRequest;
import com.tdtu.webproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class ChangePassUseCase {

    private final UserService userService;
    public String changePassword(ChangePassUseCaseInput input) {
        return userService.changePassword(this.buildChangePassRequest(input));
    }

    private ChangePassRequest buildChangePassRequest(ChangePassUseCaseInput input) {
        return ChangePassRequest.builder()
                .userId(input.getUserId())
                .lastupUserId(input.getLastupUserId())
                .passwordOld(input.getPasswordOld())
                .password(input.getPassword())
                .passwordConfirm(input.getPasswordConfirm())
                .build();
    }
}

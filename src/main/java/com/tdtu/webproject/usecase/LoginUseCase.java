package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class LoginUseCase {

    private final UserService userService;
    public String login(String email, String password) {
        return userService.login(email, password);
    }
}

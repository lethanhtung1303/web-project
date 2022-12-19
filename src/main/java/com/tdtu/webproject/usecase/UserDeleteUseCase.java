package com.tdtu.webproject.usecase;

import com.tdtu.webproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserDeleteUseCase {

    private final UserService userService;
    public String deleteUser(String userId) {
        return userService.deleteUser(userId);
    }
}

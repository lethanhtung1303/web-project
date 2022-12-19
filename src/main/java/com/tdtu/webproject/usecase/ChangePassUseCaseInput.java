package com.tdtu.webproject.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangePassUseCaseInput {
    private String userId;
    private String lastupUserId;
    private String passwordOld;
    private String password;
    private String passwordConfirm;
}

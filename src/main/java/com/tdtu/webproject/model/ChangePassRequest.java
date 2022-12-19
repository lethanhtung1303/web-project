package com.tdtu.webproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangePassRequest {
    private String userId;
    private String lastupUserId;
    private String passwordOld;
    private String password;
    private String passwordConfirm;
}

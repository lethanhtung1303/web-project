package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.*;
import com.tdtu.webproject.usecase.UserSearchUseCaseOutput.UserSearchUseCaseResults;
import generater.openapi.api.UserApi;
import generater.openapi.model.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class UserApiController implements UserApi {
    private final UserSearchUseCase userSearchUseCase;
    private final UserUpdateUseCase userUpdateUseCase;
    private final UserCreateUseCase userCreateUseCase;
    private final UserGetUseCase userGetUseCase;
    private final UserDeleteUseCase userDeleteUseCase;
    private final ChangePassUseCase changePassUseCase;
    private final LoginUseCase loginUseCase;

    @Override
    public ResponseEntity<UsersSearchResponse> searchUser(
            @ApiParam(value = "")
            @Valid
            @RequestBody UserSearchRequest userSearchRequest,
            BindingResult bindingResult1)  {
        UserSearchUseCaseOutput output = userSearchUseCase.searchUser(this.buildUserSearchUseCaseInput(userSearchRequest));
        return ResponseEntity.ok(UsersSearchResponse.builder()
                .status(HttpStatus.OK.value())
                .results(UsersSearchResponseResults.builder()
                        .users(output.getUsers().stream()
                                .map(this::buildUserResponse)
                                .collect(Collectors.toList()))
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    private UserSearchUseCaseInput buildUserSearchUseCaseInput(UserSearchRequest request) {
        return UserSearchUseCaseInput.builder()
                .email(request.getEmail())
                .stdId(request.getStdId())
                .userName(request.getUserName())
                .address(request.getAddress())
                .idCard(request.getIdCard())
                .phone(request.getPhone())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    private UserResponse buildUserResponse(UserSearchUseCaseResults results) {
        return UserResponse.builder()
                .userId(results.getUserId())
                .userName(results.getUserName())
                .approveStatus(results.getApproveStatus())
                .stdId(results.getStdId())
                .address(results.getAddress())
                .idCard(results.getIdCard())
                .fCard(results.getFCard())
                .bCard(results.getBCard())
                .portrait(results.getPortrait())
                .phone(results.getPhone())
                .email(results.getEmail())
                .password(results.getPassword())
                .roleId(results.getRoleId())
                .roleName(results.getRoleName())
                .createUserId(results.getCreateUserId())
                .createUserName(results.getCreateUserName())
                .createDatetime(results.getCreateDatetime())
                .lastupUserId(results.getLastupUserId())
                .lastupUserName(results.getLastupUserName())
                .lastupDatetime(results.getLastupDatetime())
                .build();
    }

    @Override
    public ResponseEntity<UserUpdateResponse> updateUser(
            @ApiParam(value = "")
            @Valid
            @RequestBody UserUpdateRequest userUpdateRequest,
            BindingResult bindingResult1)  {
        UserUpdateUseCaseInput input = this.buildUserUpdateUseCaseInput(userUpdateRequest);
        return ResponseEntity.ok(UserUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(userUpdateUseCase.updateUser(input))
                .build());
    }

    private UserUpdateUseCaseInput buildUserUpdateUseCaseInput(UserUpdateRequest request) {
        return UserUpdateUseCaseInput.builder()
                .userId(request.getUserId())
                .stdId(request.getStdId())
                .userName(request.getUserName())
                .address(request.getAddress())
                .idCard(request.getIdCard())
                .fCard(request.getFCard())
                .bCard(request.getBCard())
                .portrait(request.getPortrait())
                .lastupUserId(request.getLastupUserId())
                .build();
    }

    @Override
    public ResponseEntity<UserCreateResponse> createUser(
            @ApiParam(value = "")
            @Valid
            @RequestBody UserCreateRequest userCreateRequest,
            BindingResult bindingResult1) {
        UserCreateUseCaseInput input = this.buildUserUpdateUseCaseInput(userCreateRequest);
        return ResponseEntity.ok(UserCreateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(userCreateUseCase.createUser(input))
                .build());
    }

    private UserCreateUseCaseInput buildUserUpdateUseCaseInput(UserCreateRequest request) {
        return UserCreateUseCaseInput.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .roleId(request.getRoleId())
                .createUserId(request.getCreateUserId())
                .stdId(request.getStdId())
                .userName(request.getUserName())
                .address(request.getAddress())
                .idCard(request.getIdCard())
                .fCard(request.getFCard())
                .bCard(request.getBCard())
                .portrait(request.getPortrait())
                .phone(request.getPhone())
                .build();
    }

    @Override
    public ResponseEntity<UserGetResponse> getUser(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "userId", required = true)
            String userId ) {
        UserSearchUseCaseResults output = userGetUseCase.getUser(userId);
        return ResponseEntity.ok(UserGetResponse.builder()
                .status(HttpStatus.OK.value())
                .results(Optional.ofNullable(output).isPresent()
                        ? List.of(this.buildUserResponse(output))
                        : Collections.emptyList())
                .build());
    }

    @Override
    public ResponseEntity<UserDeleteResponse> deleteUser(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "userId", required = true)
            String userId ) {
        return ResponseEntity.ok(UserDeleteResponse.builder()
                .status(HttpStatus.OK.value())
                .message(userDeleteUseCase.deleteUser(userId))
                .build());
    }

    @Override
    public ResponseEntity<ChangePassResponse> changePassword(
            @ApiParam(value = "")
            @Valid
            @RequestBody ChangePassRequest changePassRequest,
            BindingResult bindingResult1) {
        ChangePassUseCaseInput input = this.buildChangePassUseCaseInput(changePassRequest);
        return ResponseEntity.ok(ChangePassResponse.builder()
                .status(HttpStatus.OK.value())
                .message(changePassUseCase.changePassword(input))
                .build());
    }

    private ChangePassUseCaseInput buildChangePassUseCaseInput(ChangePassRequest request) {
        return ChangePassUseCaseInput.builder()
                .userId(request.getUserId())
                .lastupUserId(request.getLastupUserId())
                .passwordOld(request.getPasswordOld())
                .password(request.getPassword())
                .passwordConfirm(request.getPasswordConfirm())
                .build();
    }

    @Override
    public ResponseEntity<LoginResponse> login(
            @ApiParam(value = "")
            @Valid
            @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult1) throws Exception {
        return ResponseEntity.ok(LoginResponse.builder()
                .status(HttpStatus.OK.value())
                .message(loginUseCase.login(loginRequest.getEmail(), loginRequest.getPassword()))
                .build());
    }
}

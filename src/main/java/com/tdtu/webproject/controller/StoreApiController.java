package com.tdtu.webproject.controller;

import com.tdtu.webproject.usecase.StoreGetAllUseCase;
import com.tdtu.webproject.usecase.StoreGetAllUseCaseOutput;
import com.tdtu.webproject.usecase.StoreUpdateUseCase;
import generater.openapi.api.StoreApi;
import generater.openapi.model.StoreGetAllResponse;
import generater.openapi.model.StoreGetAllResponseResults;
import generater.openapi.model.StoreUpdateRequest;
import generater.openapi.model.StoreUpdateResponse;
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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class StoreApiController implements StoreApi {
    private final StoreGetAllUseCase storeGetAllUseCase;
    private final StoreUpdateUseCase storeUpdateUseCase;

    @Override
    public ResponseEntity<StoreGetAllResponse> getAllStoreByUser(
            @NotNull @Pattern(regexp="^[0-9]{1,19}$")
            @ApiParam(value = "", required = true)
            @Validated
            @RequestParam(value = "userId", required = true)
            String userId ) {
        StoreGetAllUseCaseOutput output = storeGetAllUseCase.getAllStore(userId);
        return ResponseEntity.ok(StoreGetAllResponse.builder()
                .status(HttpStatus.OK.value())
                .results(StoreGetAllResponseResults.builder()
                        .stores(output.getStores())
                        .resultsTotalCount(output.getResultsTotalCount())
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<StoreUpdateResponse> updateStore(
            @ApiParam(value = "")
            @Valid
            @RequestBody StoreUpdateRequest storeUpdateRequest,
            BindingResult bindingResult1) {
        return ResponseEntity.ok(StoreUpdateResponse.builder()
                .status(HttpStatus.OK.value())
                .message(storeUpdateUseCase.updateStore(storeUpdateRequest.getPropertyId(), storeUpdateRequest.getCreateUserId()))
                .build());
    }
}

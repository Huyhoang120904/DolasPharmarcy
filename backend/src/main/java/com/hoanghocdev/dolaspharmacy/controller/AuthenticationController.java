package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.AuthenticationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.IntrospectTokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.TokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.AuthenticationResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.IntrospectTokenResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.service.AuthenticationService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Validated
public class AuthenticationController {

    AuthenticationService authenticationService;


    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> register(@RequestBody @Valid AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.register(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectTokenResponse> register(@RequestBody @Valid IntrospectTokenRequest request){
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody @Valid TokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refresh(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse logout(@RequestBody @Valid TokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder()
                .build();
    }



}

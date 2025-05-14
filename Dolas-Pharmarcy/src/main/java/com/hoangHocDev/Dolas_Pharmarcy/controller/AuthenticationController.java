package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.AuthenticationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.IntrospectTokenRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.TokenRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.AuthenticationResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.IntrospectTokenResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> register(@RequestBody IntrospectTokenRequest request){
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refresh(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse logout(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder()
                .build();
    }



}

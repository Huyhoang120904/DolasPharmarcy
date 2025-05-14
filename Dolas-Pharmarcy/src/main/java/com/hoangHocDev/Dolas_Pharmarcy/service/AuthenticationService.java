package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.IntrospectTokenRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.TokenRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.AuthenticationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.AuthenticationResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.IntrospectTokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectTokenResponse introspect(IntrospectTokenRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refresh(TokenRequest request) throws ParseException, JOSEException;
    void logout(TokenRequest request) throws ParseException, JOSEException;
}

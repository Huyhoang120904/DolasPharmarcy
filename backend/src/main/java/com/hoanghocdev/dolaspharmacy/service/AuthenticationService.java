package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.IntrospectTokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.TokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.AuthenticationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.AuthenticationResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.IntrospectTokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectTokenResponse introspect(IntrospectTokenRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(UserCreationRequest request);
    AuthenticationResponse refresh(TokenRequest request) throws ParseException, JOSEException;
    void logout(TokenRequest request) throws ParseException, JOSEException;
}

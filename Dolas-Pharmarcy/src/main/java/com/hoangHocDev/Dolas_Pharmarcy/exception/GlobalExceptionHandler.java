package com.hoangHocDev.Dolas_Pharmarcy.exception;

import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ApiResponse> handleOtherException(RuntimeException e) {
//
//        ErrorCode errorCode =  ErrorCode.UNCATEGORIZED_EXCEPTION;
//
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(errorCode.getMessage());
//
//        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e) {

        ErrorCode errorCode =  e.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = JwtException.class)
    ResponseEntity<ApiResponse> handleJwtException(JwtException e) {

        String message =  e.getMessage();

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }


}

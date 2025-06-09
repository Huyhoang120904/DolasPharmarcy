package com.hoanghocdev.dolaspharmacy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // SYS: System/Unknown
    UNCATEGORIZED_EXCEPTION("SYS-9999", "An unexpected error occurred. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR),

    // VAL: Validation
    INVALID_KEY("VAL-1001", "Invalid key provided.", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("VAL-1002", "Username must be at least {min} characters.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("VAL-1003", "Password must be at least {min} characters.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("VAL-1004", "The email address provided is invalid.", HttpStatus.BAD_REQUEST),
    INVALID_DOB("VAL-1005", "Your age must be at least {min}.", HttpStatus.BAD_REQUEST),
    MALFORMED_REQUEST("VAL-1006", "The request format is invalid.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD("VAL-1007", "A required field is missing: {field}.", HttpStatus.BAD_REQUEST),

    // AUT: Authentication & Authorization
    UNAUTHENTICATED("AUT-1101", "You must be authenticated to perform this action.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUT-1102", "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
    TOKEN_DECODE_FAILED("AUT-1103", "Failed to decode the provided token.", HttpStatus.UNAUTHORIZED),
    TOKEN_GENERATE_FAILED("AUT-1104", "Could not generate authentication token.", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_EXPIRED("AUT-1105", "Authentication token has expired.", HttpStatus.UNAUTHORIZED),

    // USR: User
    USER_EXISTED("USR-1201", "User already exists.", HttpStatus.CONFLICT),
    USER_NOT_EXISTED("USR-1202", "User does not exist.", HttpStatus.NOT_FOUND),
    USER_DISABLED("USR-1203", "User account is disabled.", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS("USR-1204", "Invalid username or password.", HttpStatus.UNAUTHORIZED),

    // DAT: Data/Resource
    DATA_NOT_FOUND("DAT-1301", "The requested data does not exist.", HttpStatus.NOT_FOUND),
    DUPLICATE_ENTRY("DAT-1302", "A duplicate entry exists.", HttpStatus.CONFLICT),
    PERMISSION_EXISTED("DAT-1303", "Permission already exists.", HttpStatus.CONFLICT),
    ROLE_EXISTED("DAT-1304", "Role already exists.", HttpStatus.CONFLICT),

    // BUS: Business logic/domain-specific
    STOCK_UNAVAILABLE("BUS-1401", "The requested item is out of stock.", HttpStatus.CONFLICT),
    ORDER_ALREADY_PROCESSED("BUS-1402", "This order has already been processed.", HttpStatus.CONFLICT),

    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
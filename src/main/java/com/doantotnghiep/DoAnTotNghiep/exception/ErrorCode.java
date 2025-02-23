package com.doantotnghiep.DoAnTotNghiep.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*
     * Error System
     */
    SYSTEM_ERROR("system-error", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED),

    /*
     * Error User
     */
    INVALID_USERNAME_OR_PASSWORD("invalid-username-or-password", HttpStatus.BAD_REQUEST),
    DURING_REGISTRATION_ERROR("during-registration-error", HttpStatus.BAD_REQUEST),
    SEND_EMAIL_ERROR("send-email-error", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST("email-exist", HttpStatus.BAD_REQUEST),
    USERNAME_EXIST("username-exist", HttpStatus.BAD_REQUEST),
    INVALID_OTP("invalid-otp", HttpStatus.BAD_REQUEST), // Thêm lỗi OTP không hợp lệ
    USER_NOT_FOUND("user-not-found", HttpStatus.BAD_REQUEST), // Thêm lỗi khi không tìm thấy người dùng
    NOT_FOUND("sos-not-found", HttpStatus.BAD_REQUEST), // Lỗi không tìm thấy tín hiệu sos

    SUPPLEMENT_YOURSELF("supplement-yourself", HttpStatus.BAD_REQUEST), //Lỗi thêm chính mình
    ALREADY_FRIENDS("already-friends", HttpStatus.BAD_REQUEST), //Lỗi đã là bạn bè
    NOT_FRIENDS("not-friends", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE("account-not-active", HttpStatus.FORBIDDEN), // Lỗi tài khoản chưa kích hoạt

    /*
     * Error Authentication
     */
    ERROR_JWT_IS_NOT_VALID("error-jwt-is-not-valid", HttpStatus.UNAUTHORIZED), // JWT không hợp lệ
    ERROR_ANONYMOUS_AUTHENTICATION_TOKEN("error-anonymous-authentication-token", HttpStatus.UNAUTHORIZED); // Token anonymous không hợp lệ

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

package com.doantotnghiep.DoAnTotNghiep.utils.constants;

public class EndpointConstants {
    private EndpointConstants() {
    }

    // Swagger Config
    public static final String ACTUATOR = "/actuator";
    public static final String SWAGGER_ICO = "/favicon.ico";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_VER = "/v3";
    public static final String SWAGGER_API_DOCS = SWAGGER_VER + "/api-docs";
    public static final String SWAGGER_CONFIG = "/swagger-config";

    // Email
    public static final String SWAGGER_SEND_EMAIL = "/api/email";

    // Auth Endpoints
    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String SEND_RANDOM_CODE = "/send-random-code";
    public static final String VERIFY_OTP = "/verify-otp";
}

package com.doantotnghiep.DoAnTotNghiep.utils.constants;

public class EndpointConstants {
    private EndpointConstants() {
    }

    // 🔹 Swagger Config
    public static final String ACTUATOR = "/actuator";
    public static final String SWAGGER_ICO = "/favicon.ico";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_VER = "/v3";
    public static final String SWAGGER_API_DOCS = SWAGGER_VER + "/api-docs";
    public static final String SWAGGER_CONFIG = "/swagger-config";

    // 🔹 Auth Endpoints
    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    // 🔹 OTP Endpoints
    public static final String OTP = "/otp";
    public static final String OTP_SEND = "/resend-code";
    public static final String OTP_VERIFY = "/verify-otp";// Xác minh OTP
    public static final String OTP_RESET_PASSWORD = "/reset-password";// Xác minh OTP

    //Profile Endpoints
    public static final String PROFILE = "/profile";
    public static final String PROFILE_GET = "/{userId}";  // Lấy thông tin hồ sơ
    public static final String PROFILE_EDIT = "/{userId}"; // Cập nhật hồ sơ


    // 🔹 Friend Endpoints (Danh sách bạn bè)
    public static final String FRIENDS = "/api/friends";
    public static final String FRIENDS_ADD = "/add";// Thêm bạn bè
    public static final String FRIENDS_CONFIRM = "/confirm";// Thêm bạn bè
    public static final String FRIENDS_LIST = "/list";// Danh sách bạn bè
    public static final String FRIENDS_REMOVE = "{friendId}"; // Xóa bạn bè
    public static final String FRIENDS_SEARCH = "/search";
    // SOS
    public static final String SOS_ALERTS = "/api/sos-alerts";
    public static final String SOS_LIST = "/list"; // Danh sách sos của user đó

    //
    public static final String PERSONAL_SOS_ALERTS = "/api/personal_sos_alerts";
    public static final String PERSONAL_SOS_LIST = "/list";




}

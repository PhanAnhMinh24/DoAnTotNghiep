package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.pojo.ApiResult;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.service.auth.IAuthService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.MessageConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = EndpointConstants.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping(value = EndpointConstants.SIGN_IN)
    public ResponseEntity<ApiResult<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(ApiResult.success(jwtResponse));
    }

    @PostMapping(value = EndpointConstants.SIGN_UP)
    public ResponseEntity<ApiResult<String>> registerUser(@RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest);
        return ResponseEntity.ok().body(ApiResult.success(MessageConstants.USER_REGISTERED_SUCCESSFULLY));
    }



    @PostMapping(value = EndpointConstants.VERIFY_OTP)
    public ResponseEntity<ApiResult<String>> verifyOTP(@RequestBody VerificationCodeRequest request) {
            String result = authService.verifyOTP(request);
        return ResponseEntity.ok().body(ApiResult.success(result));
    }
}

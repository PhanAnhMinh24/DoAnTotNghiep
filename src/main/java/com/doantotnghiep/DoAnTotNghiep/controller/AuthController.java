package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.ApiResult;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.service.auth.IAuthService;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.MessageConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = EndpointConstants.AUTH)
public class AuthController {
    private final IAuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(IAuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = EndpointConstants.SIGN_IN)
    public ResponseEntity<ApiResult<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(ApiResult.success(jwtResponse));
    }

    @PostMapping(value = EndpointConstants.SIGN_UP)
    public ResponseEntity<ApiResult<String>> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            authService.registerUser(signupRequest);
            return ResponseEntity.ok().body(ApiResult.success(MessageConstants.USER_REGISTERED_SUCCESSFULLY));
        } catch (Exception e) {
            throw new AppException(ErrorCode.DURING_REGISTRATION_ERROR);
        }
    }

}
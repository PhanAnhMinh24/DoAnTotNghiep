package com.doantotnghiep.DoAnTotNghiep.service.auth;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;

public interface IAuthService {
    User registerUser(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);

    String verifyOTP(VerificationCodeRequest request);
}

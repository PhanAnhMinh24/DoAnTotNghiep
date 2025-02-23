package com.doantotnghiep.DoAnTotNghiep.service.otp;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.ResetPasswordRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;

public interface IOtpService {
    void sendOTP(User user);

    String verifyOTP(VerificationCodeRequest request);

    String resetPassword(ResetPasswordRequest request);
}

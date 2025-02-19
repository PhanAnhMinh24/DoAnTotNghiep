package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.ResetPasswordRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.service.otp.IOtpService;
import com.doantotnghiep.DoAnTotNghiep.service.users.UserDetailsServiceImpl;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.OTP)
@RequiredArgsConstructor
public class OtpController {

    private final IOtpService otpService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    // 📌 Gửi OTP tới email người dùng
    @GetMapping(EndpointConstants.OTP_SEND)
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        // ✅ Lấy User trực tiếp từ database, không ép kiểu từ UserDetails
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Gửi OTP
        otpService.sendOTP(user);
        return ResponseEntity.ok("OTP đã được gửi đến email: " + email);
    }

    // 📌 Xác thực OTP
    @PostMapping(EndpointConstants.OTP_VERIFY)
    public ResponseEntity<String> verifyOtp(@RequestBody VerificationCodeRequest request) {
        String responseMessage = otpService.verifyOTP(request);
        return ResponseEntity.ok(responseMessage);
    }

    // 📌 Đổi mật khẩu sau khi xác thực OTP
    @PostMapping(EndpointConstants.OTP_RESET_PASSWORD)
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String responseMessage = otpService.resetPassword(request);
        return ResponseEntity.ok(responseMessage);
    }


}

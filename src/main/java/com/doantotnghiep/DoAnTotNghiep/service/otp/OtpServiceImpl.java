package com.doantotnghiep.DoAnTotNghiep.service.otp;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.entity.VerificationCode;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.ResetPasswordRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.VerificationCodeRepository;
import com.doantotnghiep.DoAnTotNghiep.service.mail.ISendEmailService;
import com.doantotnghiep.DoAnTotNghiep.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements IOtpService {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final ISendEmailService sendEmailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Tạo đối tượng mã hóa mật khẩu

    @Override
    public void sendOTP(User user) {
        String otp = ServiceUtils.generateRandomNumber(6);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        // Tìm OTP trong database (nếu có)
        VerificationCode verificationCode = verificationCodeRepository.findByUser(user)
                .orElse(new VerificationCode());

        // Cập nhật thông tin OTP
        verificationCode.setUser(user);
        verificationCode.setVerificationCode(otp);
        verificationCode.setExpiresAt(expiresAt); // 🔥 Cập nhật thời gian hết hạn
        verificationCode.setVerified(false);

        // Lưu vào database
        verificationCodeRepository.save(verificationCode);

        // Gửi OTP qua email
        try {
            sendEmailService.sendEmailWithOTP(user.getEmail(), otp);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SEND_EMAIL_ERROR);
        }
    }


    @Override
    public String verifyOTP(VerificationCodeRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        VerificationCode verificationCode = verificationCodeRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        // Kiểm tra mã OTP
        if (!verificationCode.getVerificationCode().equals(request.getVerificationCode()) ||
                verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        // Đánh dấu OTP đã được xác thực
        verificationCode.setVerified(true);
        verificationCodeRepository.save(verificationCode);

        // Kích hoạt tài khoản nếu chưa kích hoạt
        if (!user.isActive()) {
            user.setActive(true);
            userRepository.save(user);
        }

        return "OTP verified successfully! Account activated.";
    }
    // Phương thức reset mật khẩu
    public String resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        VerificationCode verificationCode = verificationCodeRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        // Kiểm tra mã OTP
        if (!verificationCode.getVerificationCode().equals(request.getVerificationCode()) ||
                verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        // Mã hóa mật khẩu mới
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // Mã hóa mật khẩu

        // Đổi mật khẩu và lưu vào database
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Đánh dấu OTP đã được xác thực
        verificationCode.setVerified(true);
        verificationCodeRepository.save(verificationCode);

        return "Password reset successfully!";
    }
}

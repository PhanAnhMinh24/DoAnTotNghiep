package com.doantotnghiep.DoAnTotNghiep.utils;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.entity.VerificationCode;
import com.doantotnghiep.DoAnTotNghiep.repository.VerificationCodeRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ServiceUtils {

    private static final String NUMERIC_CHARACTERS = "0123456789";

    // Phương thức sinh mã OTP ngẫu nhiên
    public static String generateRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(NUMERIC_CHARACTERS.length());
            sb.append(NUMERIC_CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    // Phương thức xử lý lưu OTP vào database
    public static void saveOtpForUser(User user, VerificationCodeRepository verificationCodeRepository) {
        String otp = generateRandomNumber(6);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        Optional<VerificationCode> existingCode = verificationCodeRepository.findByUser(user);
        VerificationCode verificationCode = existingCode.orElse(new VerificationCode());

        verificationCode.setUser(user);
        verificationCode.setVerificationCode(otp);
        verificationCode.setExpiresAt(expiresAt);
        verificationCode.setVerified(false);

        verificationCodeRepository.save(verificationCode);
    }
}

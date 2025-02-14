package com.doantotnghiep.DoAnTotNghiep.service.mail;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.entity.VerificationCode;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SendEmailService implements ISendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    private static final String NUMERIC_CHARACTERS = "0123456789";

    private String generateRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(NUMERIC_CHARACTERS.length());
            sb.append(NUMERIC_CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public void sendEmailWithOTP(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Người dùng không tồn tại!");
        }

        User user = optionalUser.get();
        String otp = generateRandomNumber(6);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        Optional<VerificationCode> existingCode = verificationCodeRepository.findByUser(user);
        VerificationCode verificationCode = existingCode.orElse(new VerificationCode());

        verificationCode.setUser(user);
        verificationCode.setVerificationCode(otp);
        verificationCode.setExpiresAt(expiresAt);
        verificationCode.setVerified(false);

        verificationCodeRepository.save(verificationCode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(email);
        message.setSubject("Mã Xác Minh");
        message.setText("Mã xác minh của bạn là: " + otp + ". Mã này có hiệu lực trong 5 phút.");

        javaMailSender.send(message);
        System.out.println("Gửi OTP thành công: " + otp);
    }
}

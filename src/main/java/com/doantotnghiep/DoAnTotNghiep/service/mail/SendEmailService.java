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

    @Override
    public void sendEmailWithOTP(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(email);
        message.setSubject("Mã Xác Minh");
        message.setText("Mã xác minh của bạn là: " + otp + ". Mã này có hiệu lực trong 5 phút.");

        javaMailSender.send(message);
        System.out.println("Gửi OTP thành công: " + otp);
    }
}

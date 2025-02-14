package com.doantotnghiep.DoAnTotNghiep.service.mail;

public interface ISendEmailService {
    void sendEmailWithOTP(String email, String otp);
}

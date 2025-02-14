package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.service.mail.ISendEmailService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = EndpointConstants.SWAGGER_SEND_EMAIL)
public class EmailController {

    @Autowired
    private ISendEmailService sendEmailService;

    @PostMapping(value = EndpointConstants.SEND_RANDOM_CODE)
    public ResponseEntity<String> sendOTP(@RequestBody VerificationCodeRequest request) {
        try {
            sendEmailService.sendEmailWithOTP(request.getEmail());
            return ResponseEntity.ok("Gửi email với mã xác minh thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gửi email thất bại: " + e.getMessage());
        }
    }
}

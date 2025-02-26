package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationCodeRequest {
  String email;
  String verificationCode; // Thêm trường để nhận OTP từ người dùng
}

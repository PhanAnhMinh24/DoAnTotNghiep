package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    String userName;     // Tên đăng nhập
    String firstName;    // Tên
    String lastName;     // Họ
    String email;        // Email
    String phoneNumber;  // Số điện thoại
    String password;     // Mật khẩu
}

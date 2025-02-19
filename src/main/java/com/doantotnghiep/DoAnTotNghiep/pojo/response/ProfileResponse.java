package com.doantotnghiep.DoAnTotNghiep.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    String firstName;
    String lastName;
    String email;  // Trả về email nhưng không có password
    String phoneNumber;
    String profileImg;
}

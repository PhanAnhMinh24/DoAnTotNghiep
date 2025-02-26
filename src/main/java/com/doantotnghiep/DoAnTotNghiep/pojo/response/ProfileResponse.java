package com.doantotnghiep.DoAnTotNghiep.pojo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProfileResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String profileImg;
}

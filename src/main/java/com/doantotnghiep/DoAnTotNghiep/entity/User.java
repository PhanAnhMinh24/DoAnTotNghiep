package com.doantotnghiep.DoAnTotNghiep.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name ="first_name")
    String firstName;

    @Column(name ="last_name")
    String lastName;

    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    String password;

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "profile_img")
    String profileImg; // Thêm trường ảnh đại diện

}

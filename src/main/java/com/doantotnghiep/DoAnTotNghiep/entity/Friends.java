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
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Liên kết với bảng Users
    User user;

    @Column(name = "friend_id", nullable = false)
    int friendId;

    @Column(name = "relation", length = 50)
    String relation; // Quan hệ (Bố, Mẹ, Bạn thân, ...)


}

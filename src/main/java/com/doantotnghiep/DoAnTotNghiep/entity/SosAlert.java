package com.doantotnghiep.DoAnTotNghiep.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "sos_alerts")
public class SosAlert extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(columnDefinition = "TEXT")
    String message;

    double latitude;
    double longitude;

    @Column(name = "time_announcement")
    LocalDateTime timeAnnouncement;

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "number_alert")
    int numberAlert;
}

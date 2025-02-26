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
public class SosAlert{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;  // Giữ nguyên kiểu dữ liệu Long như trong SQL

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    String message;

    @Column(name = "marker_id", length = 50, nullable = false)
    String markerId;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    double longitude;

    @Column(name = "time_announcement", nullable = false)
    LocalDateTime timeAnnouncement;

    @Column(name = "is_active", nullable = false)
    boolean isActive;

    @Column(name = "number_alert", nullable = false)
    int numberAlert;
}

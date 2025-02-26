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
@Table(name = "personal_sos_alerts")
public class PersonalSosAlerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "sos_alert_id", nullable = false)
    SosAlert sosAlert;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    User friend;
}

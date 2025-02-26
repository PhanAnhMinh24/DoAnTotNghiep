package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SosAlertRequest {
    String message;
    String markerId;
    double latitude;
    double longitude;
    LocalDateTime timeAnnouncement;
    boolean isActive;
    int numberAlert;
}

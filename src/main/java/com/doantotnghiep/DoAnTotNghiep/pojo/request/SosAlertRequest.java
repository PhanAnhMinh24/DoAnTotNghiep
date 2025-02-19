package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SosAlertRequest {
    private int userId;
    private String message;
    private double latitude;
    private double longitude;
}

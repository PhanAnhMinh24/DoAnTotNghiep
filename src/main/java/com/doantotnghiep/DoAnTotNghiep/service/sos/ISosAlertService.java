package com.doantotnghiep.DoAnTotNghiep.service.sos;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;

import java.util.List;

public interface ISosAlertService {
    SosAlert createSosAlert(SosAlertRequest request);
    void disableSosAlert(int id);
    List<SosAlert> getSosAlertsByUserId(int userId);
}

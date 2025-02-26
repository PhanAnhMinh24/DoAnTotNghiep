package com.doantotnghiep.DoAnTotNghiep.service.sosalert;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;

import java.util.List;

public interface ISosAlertService {
    SosAlert createSosAlert(SosAlertRequest request);

    List<SosAlert> getAlertsByUserId();


}

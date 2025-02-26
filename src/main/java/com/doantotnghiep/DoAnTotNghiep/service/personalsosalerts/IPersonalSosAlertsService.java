package com.doantotnghiep.DoAnTotNghiep.service.personalsosalerts;

import com.doantotnghiep.DoAnTotNghiep.entity.PersonalSosAlerts;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.PersonalSosAlertsRequest;
import java.util.List;

public interface IPersonalSosAlertsService {
    List<PersonalSosAlerts> createPersonalSosAlert(PersonalSosAlertsRequest request);
    List<PersonalSosAlerts> getAlertsByUserId();
}
package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.PersonalSosAlerts;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.PersonalSosAlertsRequest;
import com.doantotnghiep.DoAnTotNghiep.service.personalsosalerts.IPersonalSosAlertsService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.PERSONAL_SOS_ALERTS)
public class PersonalSosAlertsController {

    @Autowired
    private IPersonalSosAlertsService personalSosAlertsService;

    @PostMapping
    public List<PersonalSosAlerts> createPersonalSosAlert(@RequestBody PersonalSosAlertsRequest request) {
        return personalSosAlertsService.createPersonalSosAlert(request);
    }


    @GetMapping(value = EndpointConstants.PERSONAL_SOS_LIST)
    public List<PersonalSosAlerts> getAlertsByCurrentUser() {
        return personalSosAlertsService.getAlertsByUserId();
    }
}

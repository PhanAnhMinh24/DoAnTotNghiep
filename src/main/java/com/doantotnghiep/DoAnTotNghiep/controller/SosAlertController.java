package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;
import com.doantotnghiep.DoAnTotNghiep.service.sosalert.ISosAlertService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.SOS_ALERTS)
public class SosAlertController {

    @Autowired
    private ISosAlertService sosAlertService;

    @PostMapping
    public SosAlert createSosAlert(@RequestBody SosAlertRequest request) {
        return sosAlertService.createSosAlert(request);
    }


    @GetMapping(value = EndpointConstants.SOS_LIST)
    public List<SosAlert> getAlertsByCurrentUser() {
        return sosAlertService.getAlertsByUserId();
    }
}

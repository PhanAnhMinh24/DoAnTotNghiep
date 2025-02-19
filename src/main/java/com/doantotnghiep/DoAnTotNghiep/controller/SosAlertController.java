package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;
import com.doantotnghiep.DoAnTotNghiep.service.sos.ISosAlertService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.SOS_ALERTS)
@RequiredArgsConstructor
public class SosAlertController {

    private final ISosAlertService sosAlertService;

    // 🔹 Tạo tín hiệu SOS
    @PostMapping("/create")
    public ResponseEntity<SosAlert> createSosAlert(@RequestBody SosAlertRequest request) {
        SosAlert sosAlert = sosAlertService.createSosAlert(request);
        return ResponseEntity.ok(sosAlert);
    }

    // 🔹 Tắt tín hiệu SOS (Xác nhận đã về an toàn)
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableSosAlert(@PathVariable int id) {
        sosAlertService.disableSosAlert(id);
        return ResponseEntity.ok("Tín hiệu SOS đã được tắt.");
    }

    // 🔹 Lấy danh sách tín hiệu SOS của người dùng
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SosAlert>> getSosAlertsByUserId(@PathVariable int userId) {
        List<SosAlert> alerts = sosAlertService.getSosAlertsByUserId(userId);
        return ResponseEntity.ok(alerts);
    }
}

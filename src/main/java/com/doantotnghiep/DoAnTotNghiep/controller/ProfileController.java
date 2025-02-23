package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.pojo.request.ProfileRequest;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.service.profile.IProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    // Lấy thông tin hồ sơ từ token
    @GetMapping
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        String token = extractToken(request);
        return ResponseEntity.ok(profileService.getProfile(token));
    }

    // Cập nhật hồ sơ
    @PutMapping
    public ResponseEntity<User> updateProfile(HttpServletRequest request, @RequestBody ProfileRequest profileRequest) {
        String token = extractToken(request);
        return ResponseEntity.ok(profileService.updateProfile(token, profileRequest));
    }

    // Hàm hỗ trợ để lấy token từ request
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new RuntimeException("Token is missing");
    }
}

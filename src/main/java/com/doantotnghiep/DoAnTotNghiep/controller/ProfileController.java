package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.service.profile.IProfileService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.PROFILE)
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    // API lấy thông tin hồ sơ người dùng
    @GetMapping(EndpointConstants.PROFILE_GET)
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User userProfile = profileService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    // API cập nhật thông tin hồ sơ người dùng
    @PutMapping(EndpointConstants.PROFILE_EDIT)
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody User updatedUser) {
        User updatedProfile = profileService.updateUserProfile(userId, updatedUser);
        return ResponseEntity.ok(updatedProfile);
    }
}

package com.doantotnghiep.DoAnTotNghiep.service.profile;

import com.doantotnghiep.DoAnTotNghiep.entity.User;

public interface IProfileService {
    User getUserProfile(Long userId);  // Lấy thông tin hồ sơ người dùng
    User updateUserProfile(Long userId, User updatedUser);  // Cập nhật thông tin hồ sơ
}

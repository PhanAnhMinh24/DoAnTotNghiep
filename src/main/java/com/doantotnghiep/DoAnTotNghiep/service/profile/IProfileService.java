package com.doantotnghiep.DoAnTotNghiep.service.profile;

import com.doantotnghiep.DoAnTotNghiep.pojo.request.ProfileRequest;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;

import java.util.List;

public interface IProfileService {
    User getProfile(String token);
    User updateProfile(String token, ProfileRequest request);
}

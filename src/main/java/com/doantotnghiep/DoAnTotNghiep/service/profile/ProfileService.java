package com.doantotnghiep.DoAnTotNghiep.service.profile;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.ProfileRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.ProfileRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    public User getProfile(String token) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateProfile(String token, ProfileRequest request) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật thông tin
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setProfileImg(request.getProfileImg());

        return profileRepository.save(user);
    }
}

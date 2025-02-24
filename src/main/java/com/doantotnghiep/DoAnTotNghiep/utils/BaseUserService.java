package com.doantotnghiep.DoAnTotNghiep.utils;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BaseUserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = JwtUtils.getCurrentAdmin();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }

    public List<ProfileResponse> getAllProfile(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        return users.stream()
                .map(user -> ProfileResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .profileImg(user.getProfileImg())
                        .build())
                .collect(Collectors.toList());
    }
}

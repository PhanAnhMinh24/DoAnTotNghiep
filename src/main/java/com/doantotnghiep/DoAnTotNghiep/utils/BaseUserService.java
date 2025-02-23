package com.doantotnghiep.DoAnTotNghiep.utils;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}

package com.doantotnghiep.DoAnTotNghiep.service.auth;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.service.otp.IOtpService;
import com.doantotnghiep.DoAnTotNghiep.service.users.UserDetailsImpl;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final IOtpService otpService;  // Sử dụng OtpServiceImpl để gửi OTP

    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }

        User user = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .phoneNumber(signupRequest.getPhoneNumber())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .profileImg(signupRequest.getProfileImg())
                .isActive(Boolean.FALSE)
                .build();

        user = userRepository.save(user);

        // Gửi OTP thông qua OtpService
        otpService.sendOTP(user);

        return user;
    }

    @Override
    public String verifyOTP(VerificationCodeRequest request) {
        return otpService.verifyOTP(request);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD));

        // Kiểm tra tài khoản đã kích hoạt chưa
        if (!user.isActive()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVE);
        }

        // Xác thực tài khoản
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));



        // Lấy thông tin người dùng
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("email", user.getEmail());
        additionalClaims.put("firstName", user.getFirstName());
        additionalClaims.put("lastName", user.getLastName());

        String jwt = jwtUtils.generateToken(additionalClaims, user.getEmail());

        return new JwtResponse(jwt, user.getId(), user.getEmail());
    }


}
